package com.teplot.testapp.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("AppCompatCustomView")
public class MyImageView extends ImageView {
    Context context;

    float originDistance;
    float curDistance;
    float scale; //在上次基础上缩放
    float curScale = 1;
    float beginZoomScale; //开始缩放时的scale

    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    PointF curPoint = new PointF();
    PointF lastPoint = new PointF();
    public BitmapSize bitmapSize;

    private Timer closeTimer;
    private boolean isClose;
    private final float BOUNDS = 30;
    private float originX;
    private float originY;

    float smallScale;
    float bigScale;
    boolean isToBig = true;

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
//        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sb);
        // TODO Auto-generated constructor stub
    }

    public MyImageView(Context c)
    {
        super(c);
        this.context = c;
    }

    public void setBitmapSize(BitmapSize b)
    {
        this.bitmapSize = b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch(event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                curPoint.x = event.getX();
                curPoint.y = event.getY();
                savedMatrix.set(matrix);
                isClose = true;
                originX = curPoint.x;
                originY = curPoint.y;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                isClose = false;
                originDistance = getDistance(event.getX(0), event.getY(0),
                        event.getX(1), event.getY(1));
                beginZoomScale = curScale;
                break;
            case MotionEvent.ACTION_MOVE:
                if(isOutBounds(originX, originY, event.getX(0), event.getY(0)))
                {
                    isClose = false;

                }

                if(event.getPointerCount() == 2)
                {
                    curDistance = getDistance(event.getX(0), event.getY(0),
                            event.getX(1), event.getY(1));
                    scale = curDistance / originDistance;
                    curScale = beginZoomScale * scale;
                    matrix.set(savedMatrix);
                    matrix.postScale(scale,  scale
                            , (event.getX(0) + event.getX(1))/2
                            , (event.getY(0) + event.getY(1))/2);
                    this.setImageMatrix(matrix);
                }
                else if(event.getPointerCount() == 1)
                {
                    lastPoint.x = curPoint.x;
                    lastPoint.y = curPoint.y;
                    curPoint.x = event.getX();
                    curPoint.y = event.getY();
                    matrix.postTranslate(curPoint.x - lastPoint.x, curPoint.y - lastPoint.y);
                    this.setImageMatrix(matrix);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(isClose)
                {
                    if(null == closeTimer)
                    {
                        closeTimer = new Timer();
                        TimerTask task = new TimerTask(){

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                ((Activity) context).finish();
                            }

                        };
                        closeTimer.schedule(task, 500);
                    }
                    else
                    {//double click
                        closeTimer.cancel();
                        closeTimer = null;
                        if(isToBig)
                        {
                            matrix.postScale(bigScale / curScale , bigScale / curScale, event.getX(), event.getY());
                            curScale = bigScale;
                            this.setImageMatrix(matrix);
                            isToBig = false;
                        }
                        else
                        {
                            matrix.postScale(smallScale / curScale, smallScale / curScale, event.getX(), event.getY());
                            curScale = smallScale;
                            this.setImageMatrix(matrix);
                            isToBig = true;
                        }

                    }

                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if(1 == event.getActionIndex())
                {
                    curPoint.x = event.getX(0);
                    curPoint.y = event.getY(0);
                }
                else
                {
                    curPoint.x = event.getX(1);
                    curPoint.y = event.getY(1);
                }
                savedMatrix.set(matrix);
                break;
            default:
                break;
        }
        return true;
    }


    /*获得两点间距离*/
    public float getDistance(float x1, float y1, float x2, float y2)
    {
        float disX = x1 - x2;
        float disY = y1 - y2;
        return (float)Math.sqrt(disX * disX + disY * disY);
    }

    /*设置图片以合适大小居中*/
    public void center()
    {
        int viewWidth = this.getMeasuredWidth();
        int viewHeight = this.getMeasuredHeight();
        int bitmapWidth = bitmapSize.width;
        int bitmapHeight = bitmapSize.height;
        float scale = 1;
        //先居中
        matrix.setTranslate((viewWidth - bitmapWidth)/2f, (viewHeight - bitmapHeight)/2f);

        //图片宽高有大于容器, 则需要再进行一次缩放处理
        if(bitmapWidth > viewWidth || bitmapHeight > viewHeight)
        {
            if((float)bitmapWidth / bitmapHeight > (float)viewWidth / viewHeight)
            {
                //宽、高比大于容器，以宽占满容器宽度为准，进行缩放
                scale = (float)viewWidth / bitmapWidth;
                matrix.postScale(scale, scale, (float)viewWidth / 2, (float)viewHeight / 2);
            }
            else
            {
                //高、宽比大于容器， 以高占满容器高度为准，进行缩放
                scale = (float)viewHeight / bitmapHeight;
                matrix.postScale(scale, scale, (float)viewWidth / 2, (float)viewHeight / 2);
            }
        }
        smallScale = scale;
        bigScale = smallScale * 2;
        this.setImageMatrix(matrix);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        refresh();
//        center();

    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(null == bitmapSize)
        {
            return;
        }
        center();
    }

    /*刷新matrix*/
    public void refresh()
    {
        matrix.reset();
        this.setImageMatrix(matrix);
    }

    public class BitmapSize
    {
        public int width;
        public int height;

        public BitmapSize(int width, int height)
        {
            this.width = width;
            this.height = height;
        }
    }
    /*手指在屏幕上移动超过范围才被判定为滑动，否则影响点击事件的判断*/
    public boolean isOutBounds(float x1, float y1, float x2, float y2 )
    {
        return Math.abs(x2 - x1) *  Math.abs(x2 - x1)
                +  Math.abs(y2 - y1) * Math.abs(y2 - y1) > BOUNDS * BOUNDS;
    }
}

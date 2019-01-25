package com.teplot.testapp.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.teplot.testapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 两端分散对齐的Textview
 *
 */
@SuppressLint("AppCompatCustomView")
public class AlignTextView2 extends TextView {
    private int mWidth; // textView的宽度
    private float mTextHeight; // 单行文字高度
    private float mTextLineSpaceExtra = 0; // 额外的行间距
    private List<String> mSplitLines = new ArrayList<>(); // 分割后的行
    private List<Integer> mTailLines = new ArrayList<>(); // 尾行数据
    private Align mAlign = Align.ALIGN_LEFT; // 默认最后一行左对齐

    private boolean isFirstCalc = true; // 是否第一次计算
    private boolean isSetPaddingBySelf = false; // 是否自定义设置过padding
    private int mShowEllipsisLine = 0; // 第几行的内容超出后，显示省略号
    private int mOriginalHeight = 0; // 原始Height
    private int mOriginalLineCount = 0; // 原始LineCount
    private int mOriginalPaddingBottom = 0; // 原始paddingBottom
    private float mLineSpacingAdd = 0.0f;
    private float mLineSpacingMultiplier = 1.0f;

    public enum Align {
        ALIGN_LEFT, ALIGN_CENTER, ALIGN_RIGHT  // 尾行对齐方式：居左，居中，居右
    }

    public AlignTextView2(Context context) {
        super(context);
        setTextIsSelectable(false);
    }

    public AlignTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextIsSelectable(false);

        mOriginalPaddingBottom = getPaddingBottom();
        mLineSpacingMultiplier = attrs.getAttributeFloatValue("http://schemas.android.com/apk/res/android", "lineSpacingMultiplier", 1.0f);

        TypedArray typeArr = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.lineSpacingExtra});
        mLineSpacingAdd = typeArr.getDimensionPixelSize(0, 0);
        typeArr.recycle();

        TypedArray cTypeArr = context.obtainStyledAttributes(attrs, R.styleable.AlignTextView);
        int alignStyle = cTypeArr.getInt(R.styleable.AlignTextView_align, 0);
        switch (alignStyle) {
            case 0:
                mAlign = Align.ALIGN_LEFT;
                break;
            case 1:
                mAlign = Align.ALIGN_CENTER;
                break;
            case 2:
                mAlign = Align.ALIGN_RIGHT;
                break;
            default:
                break;
        }
        cTypeArr.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (isFirstCalc) { // 高度调整
            mTailLines.clear();
            mSplitLines.clear();
            mWidth = getMeasuredWidth();

            TextPaint paint = getPaint();
            String text = getText().toString();
            String[] items = text.split("\\n"); // 文本含有换行符时，分割单独处理
            for (String item : items) {
                calcTextCount(paint, item);
            }

            // 计算原始高度与行数
            measureTextViewHeight(text, paint.getTextSize(), getMeasuredWidth() - getPaddingLeft() - getPaddingRight());
            // 获取行高
            mTextHeight = 1.0f * mOriginalHeight / mOriginalLineCount;
            mTextLineSpaceExtra = mTextHeight * (mLineSpacingMultiplier - 1) + mLineSpacingAdd;
            // 计算实际高度,加上多出的行的高度(一般是减少)
            int heightGap = (int) ((mTextLineSpaceExtra + mTextHeight) * (mSplitLines.size() - mOriginalLineCount));
            //调整textview的paddingBottom来缩小底部空白
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), mOriginalPaddingBottom + heightGap);

            isFirstCalc = false;
            isSetPaddingBySelf = true;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.drawableState = getDrawableState();
        Paint.FontMetrics fm = paint.getFontMetrics();

        float firstHeight = getTextSize() - (fm.bottom - fm.descent + fm.ascent - fm.top);
        if ((getGravity() & 0x1000) == 0) { // 是否垂直居中
            firstHeight = firstHeight + (mTextHeight - firstHeight) / 2;
        }

        int paddingTop = getPaddingTop();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        mWidth = getMeasuredWidth() - paddingLeft - paddingRight;

        for (int i = 0; i < mSplitLines.size(); i++) {
            float drawSpacingX = paddingLeft; // 绘画起始x坐标
            float drawY = i * mTextHeight + firstHeight - 5;
            float gap = (mWidth - paint.measureText(mSplitLines.get(i)));
            float interval = gap / (mSplitLines.get(i).length() - 1);

            if (mTailLines.contains(i)) { // 绘制最后一行
                interval = 0;
                if (mAlign == Align.ALIGN_CENTER) {
                    drawSpacingX += gap / 2;
                } else if (mAlign == Align.ALIGN_RIGHT) {
                    drawSpacingX += gap;
                }
            }

            String lineText = mSplitLines.get(i);
            for (int j = 0; j < lineText.length(); j++) {
                float drawX = paint.measureText(lineText.substring(0, j)) + interval * j;
                if (i == mShowEllipsisLine
                        && drawX + drawSpacingX + paint.measureText(lineText.substring(j, j + 1)) == mWidth) { // 第i+1行内容超出后，显示省略号
                    canvas.drawText("...", drawX + drawSpacingX, drawY + paddingTop + mTextLineSpaceExtra * i, paint);
                } else {
                    canvas.drawText(lineText.substring(j, j + 1), drawX + drawSpacingX, drawY + paddingTop + mTextLineSpaceExtra * i, paint);
                }
            }
        }
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        if (!isSetPaddingBySelf) {
            mOriginalPaddingBottom = bottom;
        }
        isSetPaddingBySelf = false;
        super.setPadding(left, top, right, bottom);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        isFirstCalc = true;
        super.setText(text, type);
    }

    /**
     * 计算每行应显示的文本数
     *
     * @param text 要计算的文本
     */
    private void calcTextCount(Paint paint, String text) {
        if (text.length() == 0) {
            mSplitLines.add("\n");
            return;
        }

        int startPosition = 0; // 起始位置
        float oneChineseWidth = paint.measureText("启"); // 一个中文的宽度
        int ignoreCalcLength = (int) (mWidth / oneChineseWidth); // 忽略计算的长度
        StringBuilder sb = new StringBuilder(text.substring(0, Math.min(ignoreCalcLength + 1, text.length())));
        for (int i = ignoreCalcLength + 1; i < text.length(); i++) {
            if (paint.measureText(text.substring(startPosition, i + 1)) > mWidth) {
                startPosition = i;
                //将之前的字符串加入列表中
                mSplitLines.add(sb.toString());

                sb = new StringBuilder();
                //添加开始忽略的字符串，长度不足的话直接结束,否则继续
                if ((text.length() - startPosition) > ignoreCalcLength) {
                    sb.append(text.substring(startPosition, startPosition + ignoreCalcLength));
                } else {
                    mSplitLines.add(text.substring(startPosition));
                    break;
                }
                i = i + ignoreCalcLength - 1;
            } else {
                sb.append(text.charAt(i));
            }
        }

        if (sb.length() > 0) {
            mSplitLines.add(sb.toString());
        }
        mTailLines.add(mSplitLines.size() - 1);
    }

    /**
     * 获取文本实际所占高度，辅助用于计算行高,行数
     *
     * @param text        文本
     * @param textSize    字体大小
     * @param deviceWidth 屏幕宽度
     */
    private void measureTextViewHeight(String text, float textSize, int deviceWidth) {
        TextView textView = new TextView(getContext());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        textView.setText(text);
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(deviceWidth, MeasureSpec.EXACTLY);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);

        mOriginalHeight = textView.getMeasuredHeight();
        mOriginalLineCount = textView.getLineCount();
    }

    /**
     * 第几行的内容超出后，显示省略号
     *
     * @param text             文本内容
     * @param showEllipsisLine 行下标
     */
    public void setText(CharSequence text, int showEllipsisLine) {
        isFirstCalc = true;
        mShowEllipsisLine = showEllipsisLine;
        super.setText(text);
    }

    /**
     * 设置尾行对齐方式
     *
     * @param align 对齐方式
     */
    public void setAlign(Align align) {
        this.mAlign = align;
        invalidate();
    }
}
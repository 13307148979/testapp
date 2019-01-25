package com.teplot.testapp.utils;

public class TencentAIStringUtils {

    //意图描述
    public static String getTypeLabelString(int label_id) {
        String name = "";
        switch (label_id){
            case 0:
                name = "机场";
                break;
            case 1:
                name = "机舱";
                break;
            case 2:
                name = "机场航站楼";
                break;
            case 3:
                name = "胡同";
                break;
            case 4:
                name = "游乐场";
                break;
            case 5:
                name = "游乐园";
                break;

            case 6:
                name = "公寓大楼外";
                break;
            case 7:
                name = "水族馆";
                break;
            case 8:
                name = "渡槽";
                break;
            case 9:
                name = "游乐中心";
                break;
            case 10:
                name = "考古发掘";
                break;
            case 11:
                name = "档案文件";
                break;

            case 12:
                name = "曲棍球";
                break;
            case 13:
                name = "性能";
                break;
            case 14:
                name = "牛仔竞技比赛";
                break;
            case 15:
                name = "陆军基地";
                break;
            case 16:
                name = "艺术画廊";
                break;
            case 17:
                name = "艺术学校";
                break;

            case 18:
                name = "艺术工作室";
                break;
            case 19:
                name = "装配线";
                break;
            case 20:
                name = "户外田径场地";
                break;
            case 21:
                name = "阁楼";
                break;
            case 22:
                name = "大礼堂";
                break;
            case 23:
                name = "汽车厂";
                break;

            case 24:
                name = "汽车展厅";
                break;
            case 25:
                name = "荒地";
                break;
            case 26:
                name = "商店";
                break;
            case 27:
            case 139:
                name = "外部";
                break;
            case 28:
                name = "内部";
                break;
            case 29:
                name = "球坑";
                break;

            case 30:
                name = "舞厅";
                break;
            case 31:
                name = "竹林";
                break;
            case 32:
                name = "银行金库";
                break;
            case 33:
                name = "宴会厅";
                break;
            case 34:
                name = "酒吧";
                break;
            case 35:
                name = "棒球场";
                break;

            case 36:
                name = "地下室";
                break;
            case 37:
                name = "室内篮球场";
                break;
            case 38:
                name = "浴室";
                break;
            case 39:
                name = "室内市场";
                break;
            case 40:
                name = "户外市场";
                break;
            case 41:
                name = "海滩";
                break;

            case 42:
                name = "美容院";
                break;
            case 43:
                name = "卧室";
                break;
            case 44:
                name = "泊位";
                break;
            case 45:
                name = "生物学实验室";
                break;
            case 46:
                name = "木板路";
                break;
            case 47:
                name = "船的甲板上";
                break;

            case 48:
                name = "船屋";
                break;
            case 49:
                name = "书店";
                break;
            case 50:
                name = "公用电话亭里面";
                break;
            case 51:
                name = "植物园";
                break;
            case 52:
                name = "室内的弓形窗";
                break;
            case 53:
                name = "保龄球馆";
                break;

            case 54:
                name = "拳击台";
                break;
            case 55:
                name = "桥";
                break;
            case 56:
                name = "建筑立面";
                break;
            case 57:
                name = "斗牛场";
                break;
            case 58:
                name = "车内";
                break;
            case 59:
                name = "公交车站内";
                break;

            case 60:
                name = "肉店";
                break;
            case 61:
                name = "巴特";
                break;
            case 62:
                name = "小屋内";
                break;
            case 63:
                name = "自助餐厅";
                break;
            case 64:
                name = "营地";
                break;
            case 65:
                name = "校园";
                break;

            case 66:
                name = "自然的";
                break;
            case 67:
                name = "城市的";
                break;
            case 68:
                name = "糖果店";
                break;
            case 69:
                name = "峡谷";
                break;
            case 70:
                name = "汽车内饰";
                break;
            case 71:
                name = "旋转木马";
                break;

            case 72:
                name = "城堡";
                break;
            case 73:
                name = "地下墓穴";
                break;
            case 74:
                name = "墓地";
                break;
            case 75:
                name = "化学实验室";
                break;
            case 76:
                name = "孩子的房间";
                break;
            case 77:
                name = "礼堂内";
                break;

            case 78:
                name = "礼堂外";
                break;
            case 79:
                name = "教室";
                break;
            case 80:
                name = "悬崖";
                break;
            case 81:
                name = "衣柜";
                break;
            case 82:
                name = "服装店";
                break;
            case 83:
                name = "海岸";
                break;

            case 84:
                name = "驾驶舱";
                break;
            case 85:
                name = "咖啡店";
                break;
            case 86:
                name = "电脑室";
                break;
            case 87:
                name = "会议室";
                break;
            case 88:
                name = "施工现场";
                break;
            case 89:
                name = "玉米田";
                break;

            case 90:
                name = "畜栏";
                break;
            case 91:
                name = "走廊";
                break;
            case 92:
                name = "庭院";
                break;
            case 93:
                name = "小溪";
                break;
            case 94:
                name = "决口";
                break;
            case 95:
                name = "人行横道";
                break;

            case 96:
                name = "水坝";
                break;
            case 97:
                name = "熟食店";
                break;
            case 98:
                name = "百货商店";
                break;
            case 99:
                name = "沙";
                break;
            case 100:
                name = "植被";
                break;
            case 101:
                name = "沙漠公路";
                break;

            case 102:
                name = "路边小饭店";
                break;
            case 103:
            case 104:
            case 234:
                name = "餐厅";
                break;
            case 105:
                name = "迪斯科舞厅";
                break;
            case 106:
                name = "宿舍";
                break;
            case 107:
                name = "市中心";
                break;

            case 108:
                name = "更衣室";
                break;
            case 109:
                name = "车道";
                break;
            case 110:
                name = "门";
                break;
            case 111:
                name = "沙";
                break;
            case 112:
                name = "电梯大堂";
                break;
            case 113:
                name = "电梯井";
                break;

            case 114:
                name = "发动机室";
                break;
            case 115:
                name = "室内自动扶梯";
                break;
            case 116:
                name = "开挖";
                break;
            case 117:
                name = "布艺店";
                break;
            case 118:
                name = "农场";
                break;
            case 119:
                name = "快餐店";
                break;

            case 120:
                name = "栽培";
                break;
            case 121:
                name = "野生的";
                break;
            case 122:
                name = "场路";
                break;
            case 123:
                name = "火灾逃生";
                break;
            case 124:
                name = "消防站";
                break;
            case 125:
                name = "鱼塘";
                break;

            case 126:
                name = "室内跳蚤市场";
                break;
            case 127:
                name = "室内花店";
                break;
            case 128:
                name = "美食广场";
                break;
            case 129:
            case 252:
                name = "足球场";
                break;
            case 130:
                name = "阔叶";
                break;
            case 131:
                name = "森林的小路";
                break;

            case 132:
                name = "林道";
                break;
            case 133:
                name = "正式的花园";
                break;
            case 134:
                name = "喷泉";
                break;
            case 135:
                name = "厨房";
                break;
            case 136:
                name = "车库内";
                break;
            case 137:
                name = "车库外";
                break;

            case 138:
                name = "加油站";
                break;
            case 140:
                name = "杂货店内";
                break;
            case 141:
                name = "礼品店";
                break;
            case 142:
                name = "冰川";
                break;
            case 143:
                name = "高尔夫球场";
                break;

            case 144:
                name = "温室内";
                break;
            case 145:
                name = "温室外";
                break;
            case 146:
                name = "石窟";
                break;
            case 147:
                name = "体育馆内";
                break;
            case 148:
                name = "飞机棚内";
                break;
            case 149:
                name = "飞机棚外";
                break;

            case 150:
                name = "港";
                break;
            case 151:
                name = "五金店";
                break;
            case 152:
                name = "海菲尔德";
                break;
            case 153:
                name = "直升机场";
                break;
            case 154:
                name = "公路";
                break;
            case 155:
                name = "家庭办公室";
                break;

            case 156:
                name = "医院的房间";
                break;
            case 157:
                name = "温泉";
                break;
            case 158:
                name = "酒店外";
                break;
            case 159:
                name = "酒店房间";
                break;
            case 160:
                name = "房子";
                break;
            case 161:
                name = "冰淇淋店";
                break;

            case 162:
                name = "浮冰";
                break;
            case 163:
                name = "冰架";
                break;
            case 164:
                name = "室内溜冰场";
                break;
            case 165:
                name = "室外溜冰场";
                break;
            case 166:
                name = "冰山";
                break;
            case 167:
                name = "工业区";
                break;

            case 168:
                name = "胰岛";
                break;
            case 169:
                name = "浴缸里";
                break;
            case 170:
                name = "监狱";
                break;
            case 171:
                name = "日本花园";
                break;
            case 172:
                name = "珠宝店";
                break;
            case 173:
                name = "垃圾场";
                break;

            case 174:
                name = "城堡";
                break;
            case 175:
                name = "狗屋外面";
                break;
            case 176:
                name = "幼儿园的教室";
                break;
            case 177:
                name = "厨房";
                break;
            case 178:
                name = "泻湖";
                break;
            case 179:
                name = "自然的";
                break;

            case 180:
                name = "垃圾填埋";
                break;
            case 181:
                name = "降落甲板";
                break;
            case 182:
                name = "草坪";
                break;
            case 183:
                name = "图书馆室内";
                break;
            case 184:
                name = "灯塔";
                break;
            case 185:
                name = "客厅";
                break;

            case 186:
                name = "大堂";
                break;
            case 187:
                name = "更衣室";
                break;
            case 188:
                name = "商店外面";
                break;
            case 189:
                name = "商店里面";
                break;
            case 190:
            case 263:
                name = "沼泽";
                break;
            case 191:
                name = "武术馆";
                break;

            case 192:
                name = "水";
                break;
            case 193:
                name = "清真寺外面";
                break;
            case 194:
                name = "山";
                break;
            case 195:
                name = "山间小道";
                break;
            case 196:
                name = "山上的雪";
                break;
            case 197:
                name = "电影院室内";
                break;

            case 198:
                name = "博物馆室内";
                break;
            case 199:
                name = "音乐工作室";
                break;
            case 200:
                name = "自然历史博物馆";
                break;
            case 201:
                name = "婴儿室";
                break;
            case 202:
                name = "海洋";
                break;
            case 203:
                name = "办公室";
                break;

            case 204:
                name = "办公隔间";
                break;
            case 205:
                name = "石油钻台";
                break;
            case 206:
                name = "操作室";
                break;
            case 207:
                name = "果园";
                break;
            case 208:
                name = "乐池";
                break;
            case 209:
                name = "宝塔";
                break;

            case 210:
                name = "宫殿";
                break;
            case 211:
                name = "食品贮藏室";
                break;
            case 212:
                name = "公园";
                break;
            case 213:
                name = "室内停车场";
                break;
            case 214:
                name = "停车场";
                break;
            case 215:
                name = "牧场";
                break;

            case 216:
                name = "亭阁";
                break;
            case 217:
                name = "宠物店";
                break;
            case 218:
                name = "药房";
                break;
            case 219:
                name = "电话亭";
                break;
            case 220:
                name = "码头";
                break;
            case 221:
                name = "比萨店";
                break;

            case 222:
                name = "操场";
                break;
            case 223:
                name = "广场";
                break;
            case 224:
                name = "池塘";
                break;
            case 225:
                name = "酒馆内";
                break;
            case 226:
                name = "赛马场";
                break;
            case 227:
                name = "滚道";
                break;

            case 228:
                name = "筏";
                break;
            case 229:
                name = "铁路轨道";
                break;
            case 230:
                name = "雨林";
                break;
            case 231:
                name = "接待";
                break;
            case 232:
                name = "娱乐室";
                break;
            case 233:
                name = "修理店";
                break;

            case 235:
                name = "餐厅厨房";
                break;
            case 236:
                name = "餐厅的露台";
                break;
            case 237:
                name = "稻田";
                break;
            case 238:
                name = "河";
                break;
            case 239:
                name = "岩拱";
                break;


            case 240:
                name = "索桥";
                break;
            case 241:
                name = "废墟";
                break;
            case 242:
                name = "沙盒";
                break;
            case 243:
                name = "桑拿";
                break;
            case 244:
                name = "服务器机房";
                break;
            case 245:
                name = "鞋店";
                break;

            case 246:
                name = "大商场室内";
                break;
            case 247:
                name = "淋浴";
                break;
            case 248:
                name = "滑雪度假村";
                break;
            case 249:
                name = "天空";
                break;
            case 250:
                name = "摩天大楼";
                break;
            case 251:
                name = "雪地";
                break;

            case 253:
                name = "稳定的";
                break;
            case 254:
                name = "棒球";
                break;
            case 255:
                name = "足球";
                break;
            case 256:
                name = "室内舞台";
                break;
            case 257:
                name = "户外舞台";
                break;

            case 258:
                name = "楼梯";
                break;
            case 259:
                name = "街道";
                break;
            case 260:
                name = "地铁站台";
                break;
            case 261:
                name = "超市";
                break;
            case 262:
                name = "寿司店";
                break;

            case 264:
                name = "游泳池";
                break;
            case 265:
                name = "室内游泳池";
                break;
            case 266:
                name = "户外游泳池";
                break;
            case 267:
                name = "电视演播室";
                break;
            case 268:
                name = "亚洲";
                break;
            case 269:
                name = "王座室";
                break;

            case 270:
                name = "售票厅";
                break;
            case 271:
                name = "修剪花园";
                break;
            case 272:
                name = "塔";
                break;
            case 273:
                name = "玩具店";
                break;
            case 274:
                name = "列车内部";
                break;
            case 275:
                name = "火车站台";
                break;

            case 276:
                name = "林场";
                break;
            case 277:
                name = "树屋";
                break;
            case 278:
                name = "沟槽";
                break;
            case 279:
                name = "苔原";
                break;
            case 280:
                name = "海洋的深处";
                break;
            case 281:
                name = "实用的房间";
                break;

            case 282:
                name = "山谷";
                break;
            case 283:
                name = "植物园";
                break;
            case 284:
                name = "兽医办公室";
                break;
            case 285:
                name = "高架桥";
                break;
            case 286:
                name = "村庄";
                break;
            case 287:
                name = "葡萄园";
                break;

            case 288:
                name = "火山";
                break;
            case 289:
                name = "户外排球场";
                break;
            case 290:
                name = "水上公园";
                break;
            case 291:
                name = "水塔";
                break;
            case 292:
                name = "瀑布";
                break;
            case 293:
                name = "浇水洞";
                break;

            case 294:
                name = "波动";
                break;
            case 295:
                name = "小麦田";
                break;
            case 296:
                name = "风电场";
                break;
            case 297:
                name = "院子";
                break;
            case 298:
                name = "禅园";
                break;
            default:
                name = "未知";
                break;
        }
        return name;
    }
}

package com.banshengyuan.feima.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by li.liu on 2017/11/21.
 * 热闹详情
 */

public class HotFairDetailResponse implements Parcelable{


    /**
     * info : {"id":17,"name":"\u201c遛湾儿\u201d2.0，夏日浪起来！","cover_img":"http://ssapp.jixuanjk.com/uploads/5a5c7b839946893959.jpg","start_time":1500048000,"end_time":1500134400,"summary":null,"street_id":17,"street_name":"半生缘潮流街区","content":"<p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \">大多数的时候<br/> 生活都是匆匆忙忙又平淡无奇<br/> 为了避免把日子过成一潭死水<br/> 唯一的方法是靠一场仪式来帮助我们前行<br/> 于是就有了升级版7.15遛弯儿2.0<\/span><\/p><p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \">\u2014\u2014夏日浪起来轰趴集结令<\/span><\/p><p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \">当天还有浮屠市集、鲜花市集<\/span><\/p><p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \">Let Me See潮流时装周齐聚助阵<\/span><\/p><p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \"><img src=\"http://ssapp.jixuanjk.com//ueditor/php/upload/image/20180116/1516081390784186.jpg\" title=\"1516081390784186.jpg\" alt=\"4444.jpg\" width=\"318\" height=\"218\"/><\/span><\/p><p><br/><\/p><p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \">&nbsp;<\/span><\/p><p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \">本周六快来半生缘文艺潮流街区<\/span><\/p><p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \">赶一趟全城最浪的鲜花美食音乐市集<br/> 全场High&nbsp;翻天！好吃、好玩、好看\u2026\u2026<br/> 免费礼品更让你拿到手软～<\/span><\/p><p style=\"text-align: center; line-height: 2em;\"><img src=\"http://ssapp.jixuanjk.com//ueditor/php/upload/image/20180116/1516093640560842.jpg\" title=\"1516093640560842.jpg\" alt=\"未标题-2-恢复的333.jpg\" width=\"332\" height=\"235\"/><\/p><p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \">遛弯2.0<\/span><\/p><p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \">夏日浪起来周末没地儿去，<\/span><\/p><p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \">但我就是想嗨外卖吃到腻，<\/span><\/p><p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \">去哪搜罗全城美食今天不蹦迪，<\/span><\/p><p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \">明天变垃圾<\/span><\/p><p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \"><img src=\"http://ssapp.jixuanjk.com//ueditor/php/upload/image/20180116/1516081414624273.jpg\" title=\"1516081414624273.jpg\" alt=\"y567.jpg\" width=\"308\" height=\"220\"/><\/span><\/p><p><br/><\/p><p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \">7.15-7.16<\/span><\/p><p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \">合肥半生缘街区遛弯儿计划2.0<\/span><\/p><p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \">来一场不浪不拉闸的遛弯儿<\/span><\/p><p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \">粉碎你的枯燥盛夏<\/span><\/p><p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \">&nbsp;<\/span><\/p><p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \">时间：7月15日-7月16日 16:30-21:00<\/span><\/p><p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \">地点：南二环与宿松路交口绿地中心半生缘街区<\/span><\/p><p style=\"text-align: center; line-height: 2em;\"><span style=\"font-family: 微软雅黑, \"><img src=\"http://ssapp.jixuanjk.com//ueditor/php/upload/image/20180116/1516081448443400.jpg\" title=\"1516081448443400.jpg\" alt=\"微信图片_20180115162109.jpg\" width=\"242\" height=\"341\"/><\/span><\/p><p><br/><\/p>","sales_type":1,"sales_price":0,"order_sn":null,"is_collection":0,"is_sign_up":0,"status":2}
     */

    private InfoBean info;

    protected HotFairDetailResponse(Parcel in) {
        info = in.readParcelable(InfoBean.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(info, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HotFairDetailResponse> CREATOR = new Creator<HotFairDetailResponse>() {
        @Override
        public HotFairDetailResponse createFromParcel(Parcel in) {
            return new HotFairDetailResponse(in);
        }

        @Override
        public HotFairDetailResponse[] newArray(int size) {
            return new HotFairDetailResponse[size];
        }
    };

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean implements Parcelable{
        /**
         * id : 17
         * name : “遛湾儿”2.0，夏日浪起来！
         * cover_img : http://ssapp.jixuanjk.com/uploads/5a5c7b839946893959.jpg
         * start_time : 1500048000
         * end_time : 1500134400
         * summary : null
         * street_id : 17
         * street_name : 半生缘潮流街区
         * content : <p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, ">大多数的时候<br/> 生活都是匆匆忙忙又平淡无奇<br/> 为了避免把日子过成一潭死水<br/> 唯一的方法是靠一场仪式来帮助我们前行<br/> 于是就有了升级版7.15遛弯儿2.0</span></p><p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, ">——夏日浪起来轰趴集结令</span></p><p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, ">当天还有浮屠市集、鲜花市集</span></p><p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, ">Let Me See潮流时装周齐聚助阵</span></p><p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, "><img src="http://ssapp.jixuanjk.com//ueditor/php/upload/image/20180116/1516081390784186.jpg" title="1516081390784186.jpg" alt="4444.jpg" width="318" height="218"/></span></p><p><br/></p><p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, ">&nbsp;</span></p><p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, ">本周六快来半生缘文艺潮流街区</span></p><p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, ">赶一趟全城最浪的鲜花美食音乐市集<br/> 全场High&nbsp;翻天！好吃、好玩、好看……<br/> 免费礼品更让你拿到手软～</span></p><p style="text-align: center; line-height: 2em;"><img src="http://ssapp.jixuanjk.com//ueditor/php/upload/image/20180116/1516093640560842.jpg" title="1516093640560842.jpg" alt="未标题-2-恢复的333.jpg" width="332" height="235"/></p><p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, ">遛弯2.0</span></p><p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, ">夏日浪起来周末没地儿去，</span></p><p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, ">但我就是想嗨外卖吃到腻，</span></p><p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, ">去哪搜罗全城美食今天不蹦迪，</span></p><p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, ">明天变垃圾</span></p><p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, "><img src="http://ssapp.jixuanjk.com//ueditor/php/upload/image/20180116/1516081414624273.jpg" title="1516081414624273.jpg" alt="y567.jpg" width="308" height="220"/></span></p><p><br/></p><p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, ">7.15-7.16</span></p><p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, ">合肥半生缘街区遛弯儿计划2.0</span></p><p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, ">来一场不浪不拉闸的遛弯儿</span></p><p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, ">粉碎你的枯燥盛夏</span></p><p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, ">&nbsp;</span></p><p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, ">时间：7月15日-7月16日 16:30-21:00</span></p><p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, ">地点：南二环与宿松路交口绿地中心半生缘街区</span></p><p style="text-align: center; line-height: 2em;"><span style="font-family: 微软雅黑, "><img src="http://ssapp.jixuanjk.com//ueditor/php/upload/image/20180116/1516081448443400.jpg" title="1516081448443400.jpg" alt="微信图片_20180115162109.jpg" width="242" height="341"/></span></p><p><br/></p>
         * sales_type : 1
         * sales_price : 0
         * order_sn : null
         * is_collection : 0
         * is_sign_up : 0  //1：已报名   0：未报名
         * status : 2   //0未开始  1进行中  2已结束
         */

        private int id;
        private String name;
        private String cover_img;
        private int start_time;
        private int end_time;
        private String summary;
        private int street_id;
        private String street_name;
        private String content;
        private int sales_type;
        private int sales_price;
        private String order_sn;
        private int is_collection;
        private int is_sign_up;
        private int status;

        protected InfoBean(Parcel in) {
            id = in.readInt();
            name = in.readString();
            cover_img = in.readString();
            start_time = in.readInt();
            end_time = in.readInt();
            summary = in.readString();
            street_id = in.readInt();
            street_name = in.readString();
            content = in.readString();
            sales_type = in.readInt();
            sales_price = in.readInt();
            order_sn = in.readString();
            is_collection = in.readInt();
            is_sign_up = in.readInt();
            status = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(name);
            dest.writeString(cover_img);
            dest.writeInt(start_time);
            dest.writeInt(end_time);
            dest.writeString(summary);
            dest.writeInt(street_id);
            dest.writeString(street_name);
            dest.writeString(content);
            dest.writeInt(sales_type);
            dest.writeInt(sales_price);
            dest.writeString(order_sn);
            dest.writeInt(is_collection);
            dest.writeInt(is_sign_up);
            dest.writeInt(status);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<InfoBean> CREATOR = new Creator<InfoBean>() {
            @Override
            public InfoBean createFromParcel(Parcel in) {
                return new InfoBean(in);
            }

            @Override
            public InfoBean[] newArray(int size) {
                return new InfoBean[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCover_img() {
            return cover_img;
        }

        public void setCover_img(String cover_img) {
            this.cover_img = cover_img;
        }

        public int getStart_time() {
            return start_time;
        }

        public void setStart_time(int start_time) {
            this.start_time = start_time;
        }

        public int getEnd_time() {
            return end_time;
        }

        public void setEnd_time(int end_time) {
            this.end_time = end_time;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public int getStreet_id() {
            return street_id;
        }

        public void setStreet_id(int street_id) {
            this.street_id = street_id;
        }

        public String getStreet_name() {
            return street_name;
        }

        public void setStreet_name(String street_name) {
            this.street_name = street_name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getSales_type() {
            return sales_type;
        }

        public void setSales_type(int sales_type) {
            this.sales_type = sales_type;
        }

        public int getSales_price() {
            return sales_price;
        }

        public void setSales_price(int sales_price) {
            this.sales_price = sales_price;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public int getIs_collection() {
            return is_collection;
        }

        public void setIs_collection(int is_collection) {
            this.is_collection = is_collection;
        }

        public int getIs_sign_up() {
            return is_sign_up;
        }

        public void setIs_sign_up(int is_sign_up) {
            this.is_sign_up = is_sign_up;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}

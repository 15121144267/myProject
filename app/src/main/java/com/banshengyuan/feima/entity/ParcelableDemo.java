package com.banshengyuan.feima.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lei.he on 2018/5/15.
 */

public class ParcelableDemo implements Parcelable {

    private String name;
    private Integer price;

    protected ParcelableDemo(Parcel in) {
        name = in.readString();
        price = in.readInt();
    }

    public static final Creator<ParcelableDemo> CREATOR = new Creator<ParcelableDemo>() {
        @Override
        public ParcelableDemo createFromParcel(Parcel in) {
            return new ParcelableDemo(in);
        }

        @Override
        public ParcelableDemo[] newArray(int size) {
            return new ParcelableDemo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 参数是一个Parcel,用它来存储与传输数据 * @param dest
     */
    public void readFromParcel(Parcel dest) { //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        name = dest.readString();
        price = dest.readInt();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(price);
    }
}

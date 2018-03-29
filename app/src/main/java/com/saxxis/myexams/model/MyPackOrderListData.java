package com.saxxis.myexams.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by saxxis25 on 11/7/2017.
 */

public class MyPackOrderListData implements Parcelable {

    private String order_num;
    private String order_price;
    private String package_id;
    private String order_status;

    public MyPackOrderListData(String order_num, String order_price, String package_id, String order_status) {
        this.order_num = order_num;
        this.order_price = order_price;
        this.package_id = package_id;
        this.order_status = order_status;
    }

    protected MyPackOrderListData(Parcel in) {
        order_num = in.readString();
        order_price = in.readString();
        package_id = in.readString();
        order_status = in.readString();
    }

    public static final Creator<MyPackOrderListData> CREATOR = new Creator<MyPackOrderListData>() {
        @Override
        public MyPackOrderListData createFromParcel(Parcel in) {
            return new MyPackOrderListData(in);
        }

        @Override
        public MyPackOrderListData[] newArray(int size) {
            return new MyPackOrderListData[size];
        }
    };

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(order_num);
        dest.writeString(order_price);
        dest.writeString(package_id);
        dest.writeString(order_status);
    }
}

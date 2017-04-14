package com.ericwei.project333.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ericwei on 2017-04-14.
 */

public class Outfit implements Parcelable {

    private String name;
    private int outift1;
    private int outift2;
    private int outift3;
    private int outift4;
    private int outift5;
    private int outift6;
    private int outift7;
    private int outift8;
    private int outift9;
    private int outift10;

    public Outfit(String name, int outift1, int outift2, int outift3, int outift4, int outift5, int outift6, int outift7, int outift8, int outift9, int outift10) {
        this.name = name;
        this.outift1 = outift1;
        this.outift2 = outift2;
        this.outift3 = outift3;
        this.outift4 = outift4;
        this.outift5 = outift5;
        this.outift6 = outift6;
        this.outift7 = outift7;
        this.outift8 = outift8;
        this.outift9 = outift9;
        this.outift10 = outift10;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOutift1() {
        return outift1;
    }

    public void setOutift1(int outift1) {
        this.outift1 = outift1;
    }

    public int getOutift2() {
        return outift2;
    }

    public void setOutift2(int outift2) {
        this.outift2 = outift2;
    }

    public int getOutift3() {
        return outift3;
    }

    public void setOutift3(int outift3) {
        this.outift3 = outift3;
    }

    public int getOutift4() {
        return outift4;
    }

    public void setOutift4(int outift4) {
        this.outift4 = outift4;
    }

    public int getOutift5() {
        return outift5;
    }

    public void setOutift5(int outift5) {
        this.outift5 = outift5;
    }

    public int getOutift6() {
        return outift6;
    }

    public void setOutift6(int outift6) {
        this.outift6 = outift6;
    }

    public int getOutift7() {
        return outift7;
    }

    public void setOutift7(int outift7) {
        this.outift7 = outift7;
    }

    public int getOutift8() {
        return outift8;
    }

    public void setOutift8(int outift8) {
        this.outift8 = outift8;
    }

    public int getOutift9() {
        return outift9;
    }

    public void setOutift9(int outift9) {
        this.outift9 = outift9;
    }

    public int getOutift10() {
        return outift10;
    }

    public void setOutift10(int outift10) {
        this.outift10 = outift10;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.outift1);
        dest.writeInt(this.outift2);
        dest.writeInt(this.outift3);
        dest.writeInt(this.outift4);
        dest.writeInt(this.outift5);
        dest.writeInt(this.outift6);
        dest.writeInt(this.outift7);
        dest.writeInt(this.outift8);
        dest.writeInt(this.outift9);
        dest.writeInt(this.outift10);
    }

    protected Outfit(Parcel in) {
        this.name = in.readString();
        this.outift1 = in.readInt();
        this.outift2 = in.readInt();
        this.outift3 = in.readInt();
        this.outift4 = in.readInt();
        this.outift5 = in.readInt();
        this.outift6 = in.readInt();
        this.outift7 = in.readInt();
        this.outift8 = in.readInt();
        this.outift9 = in.readInt();
        this.outift10 = in.readInt();
    }

    public static final Parcelable.Creator<Outfit> CREATOR = new Parcelable.Creator<Outfit>() {
        @Override
        public Outfit createFromParcel(Parcel source) {
            return new Outfit(source);
        }

        @Override
        public Outfit[] newArray(int size) {
            return new Outfit[size];
        }
    };
}

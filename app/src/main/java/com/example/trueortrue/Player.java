package com.example.trueortrue;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {

    private String name;

    public Player(String name) {
        this.name = name;
    }

    protected Player(Parcel in) {
        name = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Player> CREATOR = new Parcelable.Creator<>() {

        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}

package com.example.kartik.testpaging.ModelClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class TrailerModel implements Parcelable {

     //long id;
     String key;
     String name;




    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected TrailerModel(Parcel in) {
        key = in.readString();
        name = in.readString();
    }

    public static final Parcelable.Creator<TrailerModel> CREATOR = new Parcelable.Creator<TrailerModel>() {
        @Override
        public TrailerModel createFromParcel(Parcel in) {
            return new TrailerModel(in);
        }

        @Override
        public TrailerModel[] newArray(int size) {
            return new TrailerModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(key);
        parcel.writeString(name);
    }
}

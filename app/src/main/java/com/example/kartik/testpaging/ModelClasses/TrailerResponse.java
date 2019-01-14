package com.example.kartik.testpaging.ModelClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class TrailerResponse implements Parcelable {

    long id;
    List<TrailerModel> results;

    protected TrailerResponse(Parcel in) {
        id = in.readLong();
        results = in.createTypedArrayList(TrailerModel.CREATOR);
    }

    public static final Creator<TrailerResponse> CREATOR = new Creator<TrailerResponse>() {
        @Override
        public TrailerResponse createFromParcel(Parcel in) {
            return new TrailerResponse(in);
        }

        @Override
        public TrailerResponse[] newArray(int size) {
            return new TrailerResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeTypedList(results);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<TrailerModel> getResults() {
        return results;
    }

    public void setResults(List<TrailerModel> results) {
        this.results = results;
    }
}

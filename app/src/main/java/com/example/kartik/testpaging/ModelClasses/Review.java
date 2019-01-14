package com.example.kartik.testpaging.ModelClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Review implements Parcelable {

    long id;
    List<ReviewModel> results;

    protected Review(Parcel in) {
        id = in.readLong();
        results = in.createTypedArrayList(ReviewModel.CREATOR);
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
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

    public List<ReviewModel> getResults() {
        return results;
    }

    public void setResults(List<ReviewModel> results) {
        this.results = results;
    }
}

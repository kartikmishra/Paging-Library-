package com.example.kartik.testpaging.ModelClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ReviewResponse implements Parcelable {


    long id;
    List<ReviewModel> results;


    protected ReviewResponse(Parcel in) {
        id = in.readLong();
        results = in.createTypedArrayList(ReviewModel.CREATOR);
    }

    public static final Creator<ReviewResponse> CREATOR = new Creator<ReviewResponse>() {
        @Override
        public ReviewResponse createFromParcel(Parcel in) {
            return new ReviewResponse(in);
        }

        @Override
        public ReviewResponse[] newArray(int size) {
            return new ReviewResponse[size];
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

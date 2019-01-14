package com.example.kartik.testpaging.ModelClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewModel implements Parcelable {



    String author;
    String content;
    //long id;

    protected ReviewModel(Parcel in) {
        author = in.readString();
        content = in.readString();
      //  id = in.readLong();
    }

    public static final Creator<ReviewModel> CREATOR = new Creator<ReviewModel>() {
        @Override
        public ReviewModel createFromParcel(Parcel in) {
            return new ReviewModel(in);
        }

        @Override
        public ReviewModel[] newArray(int size) {
            return new ReviewModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(content);
      //  parcel.writeLong(id);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    //public long getId() {
      //  return id;
    //}

    //public void setId(long id) {
      //  this.id = id;
    //}
}

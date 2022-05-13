package ru.akimychev.mynotes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Notes implements Parcelable {

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };
    private final String name;
    private final String description;
    private Date date;

    public Notes(String name, String description, Date date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    protected Notes(Parcel in) {
        name = in.readString();
        description = in.readString();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
    }
}

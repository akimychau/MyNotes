package ru.akimychev.mynotes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Objects;

public class Note implements Parcelable {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(name, note.name) && Objects.equals(description, note.description) && Objects.equals(date, note.date);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, description, date);
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
    private final String name;
    private final String description;
    private final Date date;

    public Note(String name, String description, Date date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    protected Note(Parcel in) {
        name = in.readString();
        description = in.readString();
        date = new Date(in.readLong());
    }

    public String getTitle() {
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
        parcel.writeLong(date.getTime());

    }
}

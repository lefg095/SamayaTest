package com.example.samayatest.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Pictures")
public class PicturesRoom implements Parcelable {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "author")
    private String author;
    @ColumnInfo(name = "width")
    private long width;
    @ColumnInfo(name = "height")
    private long height;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "download_url")
    private String download_url;


    public PicturesRoom() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }




    protected PicturesRoom(Parcel in) {
        id = in.readLong();
        author = in.readString();
        width = in.readLong();
        height = in.readLong();
        url = in.readString();
        download_url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(author);
        dest.writeLong(width);
        dest.writeLong(height);
        dest.writeString(url);
        dest.writeString(download_url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PicturesRoom> CREATOR = new Creator<PicturesRoom>() {
        @Override
        public PicturesRoom createFromParcel(Parcel in) {
            return new PicturesRoom(in);
        }

        @Override
        public PicturesRoom[] newArray(int size) {
            return new PicturesRoom[size];
        }
    };
}

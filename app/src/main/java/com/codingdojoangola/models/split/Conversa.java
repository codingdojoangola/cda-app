package com.codingdojoangola.models.split;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jadev on 14-12-2017.
 */

public class Conversa implements Parcelable{

    @SerializedName("message")
    private String message;
    @SerializedName("sender")
    private String sender;
    @SerializedName("date_time")
    private String date_time;

    public static final Creator<Conversa> CREATOR = new Creator<Conversa>() {
        @Override
        public Conversa createFromParcel(Parcel in) {
            return new Conversa(in);
        }

        @Override
        public Conversa[] newArray(int size) {
            return new Conversa[size];
        }
    };

    public Conversa(){
        
    }

    protected Conversa(Parcel in) {
        sender = in.readString();
        message = in.readString();
        date_time = in.readString();
    }

    public Conversa(String message, String sender, String date_time) {
        this.message = message;
        this.sender = sender;
        this.date_time = date_time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sender);
        dest.writeString(message);
        dest.writeString(date_time);
    }
}

package com.codingdojoangola.models.chat;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jadev on 14-12-2017.
 */

public class Message implements Parcelable{

    @SerializedName("message")
    private String message;
    @SerializedName("sender")
    private String sender;
    @SerializedName("date_time")
    private String date_time;

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public Message(){
        
    }

    protected Message(Parcel in) {
        sender = in.readString();
        message = in.readString();
        date_time = in.readString();
    }

    public Message(String message, String sender, String date_time) {
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

package app.kth.com.groupie.Data.Structure;

import java.text.DateFormat;

/**
 * Created by Ahmad on 4/11/2018.
 */

public class Message {
    private String senderUserId;
    private String name;
    private String senderPicture;
    private String text;
    private String imageUrl;
    private DateFormat timeSent;

    public Message() {}

    public Message(String senderUserId, String name, String senderPicture, String text, String imageUrl, DateFormat timeSent) {
        this.senderUserId = senderUserId;
        this.name = name;
        this.senderPicture = senderPicture;
        this.text = text;
        this.imageUrl = imageUrl;
        this.timeSent = timeSent;
    }

    public String getSenderUserId() { return senderUserId; }

    public void setSenderUserId(String senderUserId) {
        this.senderUserId = senderUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSenderPicture() {
        return senderPicture;
    }

    public void setSenderPicture(String senderPicture) {
        this.senderPicture = senderPicture;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public DateFormat getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(DateFormat timeSent) {
        this.timeSent = timeSent;
    }
}

package com.spiderindia.departmentsofhighway.Details;

/**
 * Created by pyr on 16-Aug-18.
 */

public class EventListDetails {
    String id,title,content,date;
    public EventListDetails(String id_, String title_, String content_, String date_) {
        this.id=id_;
        this.title=title_;
        this.content=content_;
        this.date=date_;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

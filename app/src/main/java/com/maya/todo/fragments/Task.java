package com.maya.todo.fragments;

import java.util.Date;


public class Task implements Comparable<Task> {
    private String title;
    private String desc;
    private Date dueDate;
    private String category;
    private int     notificationId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public Task(String title, String desc, Date dueDate, int notificationId, String category) {
        this.title = title;
        this.desc = desc;
        this.dueDate = dueDate;
        this.category = category;
        this.notificationId = notificationId;
    }

    @Override
    public int compareTo(Task o) {
        return getDueDate().compareTo(o.getDueDate());
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Task))
            return false;
        Task other = (Task) obj;
        return notificationId == other.getNotificationId();
    }
}

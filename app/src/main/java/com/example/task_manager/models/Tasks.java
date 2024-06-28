package com.example.task_manager.models;

import java.util.Date;

public class Tasks {
    private int id;
    private String task_name;
    private int task_important;
    private int task_urgency;
    private Date reminder;
    private boolean status_task;

    public Tasks(int id, String task_name, int task_important, int task_urgency, Date reminder, boolean status_task) {
        this.id = id;
        this.task_name = task_name;
        this.task_important = task_important;
        this.task_urgency = task_urgency;
        this.reminder = reminder;
        this.status_task = status_task;
    }

    public boolean isStatus_task() {
        return status_task;
    }

    public void setStatus_task(boolean status_task) {
        this.status_task = status_task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public int getTask_important() {
        return task_important;
    }

    public void setTask_important(int task_important) {
        this.task_important = task_important;
    }

    public int getTask_urgency() {
        return task_urgency;
    }

    public void setTask_urgency(int task_urgency) {
        this.task_urgency = task_urgency;
    }

    public Date getReminder() {
        return reminder;
    }

    public void setReminder(Date reminder) {
        this.reminder = reminder;
    }
}

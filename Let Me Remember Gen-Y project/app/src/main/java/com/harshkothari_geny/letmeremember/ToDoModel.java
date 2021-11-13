package com.harshkothari_geny.letmeremember;

public class ToDoModel {
    String title;
    String description;
    boolean isCompleted;

    public ToDoModel(String title, String description, boolean isCompleted) {
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}

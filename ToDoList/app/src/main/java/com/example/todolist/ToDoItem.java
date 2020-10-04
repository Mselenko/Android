package com.example.todolist;

public class ToDoItem {

    private String description;
    private boolean isComplete;
    private long id;
    private String timestamp;


    public ToDoItem(String description, boolean isComplete, String timestamp){
        this(description,isComplete,-1, timestamp);
    }

    public ToDoItem(String description, boolean isComplete, long id, String timestamp){
        this.description = description;
        this.isComplete = isComplete;
        this.id = id;
        this.timestamp = timestamp;
    }

    public boolean isComplete(){
        return isComplete;
    }

    public void toggleComplete(){
        isComplete = !isComplete;
    }

    public String getDescription(){
        return description;
    }

    public long getId(){ return id; }

    @Override
    public String toString(){
        return getDescription();
    }

    public String getTimestamp(){ return timestamp;}
}

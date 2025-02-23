package com.vp.simpleTaskTddApp.exception;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String messge){
        super(messge);
    }
}

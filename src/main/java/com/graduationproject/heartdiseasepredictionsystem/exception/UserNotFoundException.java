package com.graduationproject.heartdiseasepredictionsystem.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message){
        super(message);
    }
}

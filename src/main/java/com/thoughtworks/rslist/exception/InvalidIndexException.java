package com.thoughtworks.rslist.exception;

/**
 * Created by wzw on 2020/8/5.
 */
public class InvalidIndexException extends Exception{
    String message;

    public InvalidIndexException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}

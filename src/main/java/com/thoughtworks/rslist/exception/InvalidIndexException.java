package com.thoughtworks.rslist.exception;

import lombok.Data;

/**
 * Created by wzw on 2020/8/5.
 */
@Data
public class InvalidIndexException extends Exception{
    String message;
    @Override
    public String getMessage(){
        return "index out 0f range";
    }
}

package com.thoughtworks.rslist.componet;

import com.thoughtworks.rslist.exception.Error;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by wzw on 2020/8/5.
 */
@ControllerAdvice
public class RsEventExceptionHandler {
    @ExceptionHandler({InvalidIndexException.class, MethodArgumentNotValidException.class})
    public ResponseEntity exceptionHandler(Exception e){
        String errorMessage;
        if(e instanceof InvalidIndexException){
            errorMessage = e.getMessage();
        }else {
            errorMessage = "invalid param";
        }
        Error error = new Error();
        error.setError(errorMessage);
        return ResponseEntity.badRequest().body(error);
    }
}

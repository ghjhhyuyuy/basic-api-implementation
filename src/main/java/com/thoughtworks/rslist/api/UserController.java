package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.exception.Error;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzw on 2020/8/5.
 */
@RestController
public class UserController {
    List<User> userList = new ArrayList<>();
    @PostMapping("/user")
    public ResponseEntity addUser(@RequestBody @Valid User user){
        if(!userList.contains(user)){
            userList.add(user);
        }
        return ResponseEntity.created(null).header("index", String.valueOf(userList.size() - 1)).build();
    }
    @GetMapping("/users")
    public ResponseEntity addUser(){
        return ResponseEntity.ok(userList);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity exceptionHandler(Exception e){
        Error error = new Error();
        error.setError("invalid user");
        return ResponseEntity.badRequest().body(error);
    }
}

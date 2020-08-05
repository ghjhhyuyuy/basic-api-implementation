package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzw on 2020/8/5.
 */
@RestController
public class UserController {
    List<User> userList = new ArrayList<>();
    @PostMapping("/rs/user/add")
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
}

package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
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
    public void addUser(@RequestBody @Valid User user){
        if(!userList.contains(user)){
            userList.add(user);
        }
    }
}
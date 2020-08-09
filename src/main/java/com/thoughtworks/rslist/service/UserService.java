package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wzw on 2020/8/9.
 */
public interface UserService {
    public void addUser(User user) throws InvalidIndexException;

    public List<UserDto> getAllUser();

    public UserDto getUserByIndex(int index);

    public void deleteUserByIndex(int index);
}

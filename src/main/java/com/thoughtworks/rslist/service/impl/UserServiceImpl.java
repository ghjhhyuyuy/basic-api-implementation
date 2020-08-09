package com.thoughtworks.rslist.service.impl;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by wzw on 2020/8/9.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public void addUser(User user) throws InvalidIndexException {
        List<UserDto> userDtoList = userRepository.findAll();
        UserDto userDto = UserDto.builder().email(user.getEmail()).gender(user.getGender())
                .age(user.getAge()).phone(user.getPhone()).userName(user.getName()).voteNum(user.getVoteNum()).build();
        if (!userDtoList.contains(userDto)) {
            userRepository.save(userDto);
        } else {
            throw new InvalidIndexException("Repeated Id");
        }
    }

    @Override
    public List<UserDto> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserByIndex(int index) {
        Optional<UserDto> optionalUserDto = userRepository.findById(index);
        return optionalUserDto.orElse(null);
    }

    @Override
    public void deleteUserByIndex(int index) {
        userRepository.deleteById(index);
    }
}

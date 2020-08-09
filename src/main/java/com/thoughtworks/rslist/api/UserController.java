package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.exception.Error;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by wzw on 2020/8/5.
 */
@RestController
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    public ResponseEntity addUser(@RequestBody @Valid User user) throws InvalidIndexException {
        List<UserDto> userDtoList = userRepository.findAll();
        UserDto userDto = UserDto.builder().email(user.getEmail()).gender(user.getGender())
                .age(user.getAge()).phone(user.getPhone()).userName(user.getName()).voteNum(user.getVoteNum()).build();
        if (!userDtoList.contains(userDto)) {
            userRepository.save(userDto);
            return ResponseEntity.created(null).header("index", String.valueOf(userDtoList.size() + 1)).build();
        } else {
            throw new InvalidIndexException("Repeated Id");
        }

    }

    @GetMapping("/users")
    public ResponseEntity getAllUser() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/user/{index}")
    public ResponseEntity getUserByIndex(@PathVariable int index) {
        return ResponseEntity.ok(userRepository.findById(index));
    }

    @DeleteMapping("/user/delete/{index}")
    public ResponseEntity deleteUserByIndex(@PathVariable int index) {
        userRepository.deleteById(index);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity exceptionHandler(Exception e) {
        Error error = new Error();
        error.setError("invalid user");
        logger.error("invalid user");
        return ResponseEntity.badRequest().body(error);
    }
}

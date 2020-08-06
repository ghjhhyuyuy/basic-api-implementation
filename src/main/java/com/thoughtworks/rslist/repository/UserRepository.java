package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.UserDto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by wzw on 2020/8/6.
 */
public interface UserRepository extends CrudRepository<UserDto,Integer> {
    @Override
    List<UserDto> findAll();

}

package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.UserDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by wzw on 2020/8/6.
 */
public interface UserRepository extends CrudRepository<UserDto, Integer> {
    @Override
    List<UserDto> findAll();

    @Query(value = "ALTER TABLE user AUTO_INCREMENT=1", nativeQuery = true)
    @Modifying
    @Transactional
    void resetAutoIncrement();
}

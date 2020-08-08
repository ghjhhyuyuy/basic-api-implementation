package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.dto.VoteDto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by wzw on 2020/8/8.
 */
public interface VoteRepository extends CrudRepository<VoteDto,Integer> {
    @Override
    List<VoteDto> findAll();
}

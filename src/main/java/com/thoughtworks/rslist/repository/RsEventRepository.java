package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.RsEventDto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by wzw on 2020/8/6.
 */
public interface RsEventRepository extends CrudRepository<RsEventDto, Integer> {
    @Override
    List<RsEventDto> findAll();
}

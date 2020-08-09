package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.dto.RsEventDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by wzw on 2020/8/6.
 */
public interface RsEventRepository extends CrudRepository<RsEventDto, Integer> {
    @Override
    List<RsEventDto> findAll();

    RsEventDto findByUserDtoId(int userDtoId);
    @Query(value="ALTER TABLE rs_event_dto AUTO_INCREMENT=1", nativeQuery = true)
    @Modifying
    @Transactional
    void resetAutoIncrement();
}

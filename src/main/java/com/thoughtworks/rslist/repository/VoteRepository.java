package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.VoteDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by wzw on 2020/8/8.
 */
public interface VoteRepository extends CrudRepository<VoteDto, Integer> {
    @Override
    List<VoteDto> findAll();

    @Query(value = "ALTER TABLE vote AUTO_INCREMENT=1", nativeQuery = true)
    @Modifying
    @Transactional
    void resetAutoIncrement();
}

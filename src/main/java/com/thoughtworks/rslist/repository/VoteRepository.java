package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.VoteDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;
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

    @Query(value = "SELECT * FROM vote WHERE vote_time  BETWEEN :startTime AND :endTime", nativeQuery = true)
    List<VoteDto> findVotesByStartAndEnd(@Param("startTime") Timestamp startTime, @Param("endTime") Timestamp endTime);
}

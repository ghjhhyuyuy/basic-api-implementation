package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.VoteDto;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by wzw on 2020/8/9.
 */
public interface VoteService {
    public void addVote(int rsEventId, Vote vote);

    List<VoteDto> getVoteBeforeStartAndEnd(Timestamp startTime, Timestamp endTime) throws Exception;
}

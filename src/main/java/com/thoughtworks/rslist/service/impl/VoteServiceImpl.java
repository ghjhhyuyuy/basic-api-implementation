package com.thoughtworks.rslist.service.impl;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import com.thoughtworks.rslist.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by wzw on 2020/8/9.
 */
@Service
public class VoteServiceImpl implements VoteService {
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RsEventRepository rsEventRepository;

    @Override
    public void addVote(int rsEventId, Vote vote) {
        VoteDto voteDto = new VoteDto();
        voteDto.setVoteNum(vote.getVoteNum());
        voteDto.setVoteTime(vote.getVoteTime());
        voteDto.setRsEventDto(rsEventRepository.findById(vote.getRsEventId()).get());
        voteDto.setUserDto(userRepository.findById(vote.getUserId()).get());
        voteRepository.save(voteDto);
    }

    @Override
    public List<VoteDto> getVoteBeforeStartAndEnd(Timestamp startTime, Timestamp endTime) throws Exception {
        if(startTime.after(endTime)){
            throw new Exception("invalid request param");
        }
        return voteRepository.findVotesByStartAndEnd(startTime,endTime);
    }
}

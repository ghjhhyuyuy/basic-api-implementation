package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wzw on 2020/8/6.
 */
@RestController
public class VoteController {
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RsEventRepository rsEventRepository;

    @PostMapping("/rs/vote/{rsEventId}")
    public ResponseEntity addVote(@PathVariable int rsEventId, @RequestBody Vote vote) throws InvalidIndexException {
        VoteDto voteDto = new VoteDto();
        voteDto.setVoteNum(vote.getVoteNum());
        voteDto.setVoteTime(vote.getVoteTime());
        voteDto.setRsEventDto(rsEventRepository.findById(vote.getRsEventId()).get());
        voteDto.setUserDto(userRepository.findById(vote.getUserId()).get());
        voteRepository.save(voteDto);
        return ResponseEntity.created(null).build();
    }
}

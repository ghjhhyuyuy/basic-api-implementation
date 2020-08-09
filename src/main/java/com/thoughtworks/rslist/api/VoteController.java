package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by wzw on 2020/8/6.
 */
@RestController
public class VoteController {
    @Autowired
    private VoteService voteService;

    @PostMapping("/rs/{rsEventId}/vote")
    public ResponseEntity addVote(@PathVariable int rsEventId, @RequestBody Vote vote) throws InvalidIndexException {
        voteService.addVote(rsEventId, vote);
        return ResponseEntity.created(null).build();
    }
    @GetMapping("/rs/voteBetween")
    public ResponseEntity getVoteBeforeStartAndEnd(@RequestParam Timestamp startTime,@RequestParam Timestamp endTime) throws Exception {
        List<VoteDto> voteDtoList = voteService.getVoteBeforeStartAndEnd(startTime,endTime);
        return ResponseEntity.ok(voteDtoList);
    }
}

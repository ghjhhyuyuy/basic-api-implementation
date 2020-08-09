package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.service.VoteService;
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
    private VoteService voteService;

    @PostMapping("/rs/vote/{rsEventId}")
    public ResponseEntity addVote(@PathVariable int rsEventId, @RequestBody Vote vote) throws InvalidIndexException {
        voteService.addVote(rsEventId, vote);
        return ResponseEntity.created(null).build();
    }
}

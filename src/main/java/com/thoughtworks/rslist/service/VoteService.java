package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.Vote;

/**
 * Created by wzw on 2020/8/9.
 */
public interface VoteService {
    public void addVote(int rsEventId, Vote vote);
}

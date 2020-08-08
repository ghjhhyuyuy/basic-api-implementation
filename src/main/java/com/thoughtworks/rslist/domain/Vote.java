package com.thoughtworks.rslist.domain;

import lombok.Data;

import java.sql.Timestamp;


/**
 * Created by wzw on 2020/8/8.
 */
@Data
public class Vote {
    int rsEventId;
    Timestamp voteTime;
    int voteNum;
    int userId;

    public Vote(int rsEventId, Timestamp voteTime, int voteNum, int userId) {
        this.rsEventId = rsEventId;
        this.voteTime = voteTime;
        this.voteNum = voteNum;
        this.userId = userId;
    }
}

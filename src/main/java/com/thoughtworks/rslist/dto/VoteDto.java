package com.thoughtworks.rslist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by wzw on 2020/8/8.
 */
@Entity
@Data
@Table(name = "vote")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    Timestamp voteTime;
    int voteNum;
    @ManyToOne
    private UserDto userDto;
    @ManyToOne
    private RsEventDto rsEventDto;
}

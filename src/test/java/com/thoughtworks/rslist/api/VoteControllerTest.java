package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Time;
import java.sql.Timestamp;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by wzw on 2020/8/6.
 */
@SpringBootTest
@AutoConfigureMockMvc
class VoteControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    VoteRepository voteRepository;
    UserDto userDto;
    RsEventDto rsEventDto;
    VoteDto voteDto;
    VoteDto voteDto1;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder().email("wzw@qq.com").gender("male")
                .age(22).phone("18888888888").userName("wzw").voteNum(10).build();
        userDto = userRepository.save(userDto);
        rsEventDto = RsEventDto.builder().keyWord("经济").eventName("大爆炸").userDto(userDto).build();
        rsEventDto = rsEventRepository.save(rsEventDto);
        Timestamp time = new Timestamp(2016, 11, 7, 5, 12, 22, 0);
        Timestamp time1 = new Timestamp(2020, 11, 7, 5, 12, 22, 0);
        voteDto = VoteDto.builder().voteNum(2).voteTime(time).rsEventDto(rsEventDto).userDto(userDto).build();
        voteDto = voteRepository.save(voteDto);
        voteDto1 = VoteDto.builder().voteNum(2).voteTime(time1).rsEventDto(rsEventDto).userDto(userDto).build();
        voteDto1 = voteRepository.save(voteDto1);
    }

    @Test
    void should_add_vote_information() throws Exception {
        Vote vote = new Vote(1, new Timestamp(System.currentTimeMillis()), 1, 1);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(vote);
        mockMvc.perform(post("/rs/vote/2").content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void should_get_vote_when_given_start_and_end_time() throws Exception {
        Timestamp startTime = new Timestamp(2016, 11, 7, 5, 12, 22, 0);
        Timestamp endTime = new Timestamp(2017, 11, 7, 5, 12, 22, 0);
        mockMvc.perform(get("/rs/vote?startTime=" + startTime + "&endTime=" + endTime))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk());
    }

    @AfterEach
    void tearDown() {
        voteRepository.deleteAll();
        rsEventRepository.deleteAll();
        userRepository.deleteAll();
        voteRepository.resetAutoIncrement();
        rsEventRepository.resetAutoIncrement();
        userRepository.resetAutoIncrement();
    }
}
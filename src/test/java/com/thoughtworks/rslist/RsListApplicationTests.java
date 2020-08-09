package com.thoughtworks.rslist;

import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RsListApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RsEventRepository rsEventRepository;
    @Autowired
    VoteRepository voteRepository;
    UserDto userDto;
    RsEventDto rsEventDto;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder().email("wzw@qq.com").gender("male")
                .age(22).phone("18888888888").userName("wzw").voteNum(10).id(1).build();
        userDto = userRepository.save(userDto);
        rsEventDto = RsEventDto.builder().keyWord("经济").eventName("大爆炸").userDto(userDto).id(1).build();
        rsEventDto = rsEventRepository.save(rsEventDto);
    }

    @Test
    @Order(1)
    void get_rs_event_list() throws Exception {
        mockMvc.perform(get("/rs/list")).andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].eventName", is("大爆炸")))
                .andExpect(jsonPath("$[0].keyWord", is("经济")))
                .andExpect(jsonPath("$[0]", not(hasKey("user"))))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void get_rs_event_number() throws Exception {
        mockMvc.perform(get("/rs/1"))
                .andExpect(jsonPath("$.eventName", is("大爆炸")))
                .andExpect(jsonPath("$.keyWord", is("经济")))
                .andExpect(jsonPath("$", not(hasKey("user"))))
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void get_rs_event_between() throws Exception {
        mockMvc.perform(get("/rs/listBetween?start=1&end=1"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].eventName", is("大爆炸")))
                .andExpect(jsonPath("$[0].keyWord", is("经济")))
                .andExpect(jsonPath("$[0]", not(hasKey("user"))))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void should_add_reEvent_when_user_exit() throws Exception {
        UserDto save = userRepository.save(UserDto.builder().email("wzw@qq.com").gender("male")
                .age(22).phone("18888888888").userName("wzw").voteNum(10).build());
        String jsonString = "{\"eventName\":\"猪肉涨价啦\",\"keyWords\":\"经济\",\"userId\":" + save.getId() + "}";
        mockMvc.perform(post("/rs/event").content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        List<RsEventDto> all = rsEventRepository.findAll();
        assertNotNull(all);
        assertEquals(2, all.size());
        assertEquals("猪肉涨价啦", all.get(1).getEventName());
        assertEquals("经济", all.get(1).getKeyWord());
        assertEquals(save.getId(), all.get(1).getId());
    }

    @Test
    @Order(5)
    void should_not_add_rs_event_user_not_exist() throws Exception {
        UserDto save = userRepository.save(UserDto.builder().email("wzw@qq.com").gender("male")
                .age(22).phone("18888888888").userName("wzw").voteNum(10).build());
        String jsonString = "{\"eventName\":\"猪肉涨价啦\",\"keyWords\":\"经济\",\"userId\":100}";
        mockMvc.perform(post("/rs/event").content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        List<RsEventDto> all = rsEventRepository.findAll();
        assertEquals(1, all.size());
    }

    @Test
    @Order(6)
    void update_rs_event() throws Exception {
        mockMvc.perform(patch("/rs/1?eventName=房价降啦&userId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/1"))
                .andExpect(jsonPath("$.eventName", is("房价降啦")))
                .andExpect(jsonPath("$.keyWord", is("经济")))
                .andExpect(status().isOk());
        mockMvc.perform(patch("/rs/1?keyWords=战争&userId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/1"))
                .andExpect(jsonPath("$.eventName", is("房价降啦")))
                .andExpect(jsonPath("$.keyWord", is("战争")))
                .andExpect(status().isOk());
        mockMvc.perform(patch("/rs/1?eventName=第一条事件&keyWords=无标签&userId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/1"))
                .andExpect(jsonPath("$.eventName", is("第一条事件")))
                .andExpect(jsonPath("$.keyWord", is("无标签")))
                .andExpect(status().isOk());
    }

    @Test
    @Order(7)
    void delete_rs_event() throws Exception {
        mockMvc.perform(delete("/rs/delete/2"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list")).andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].eventName", is("大爆炸")))
                .andExpect(jsonPath("$[0].keyWord", is("经济")))
                .andExpect(jsonPath("$[0]", not(hasKey("user"))))
                .andExpect(status().isOk());
    }

    @Test
    void should_return_400_and_message_when_index_out_of_range() throws Exception {
        mockMvc.perform(get("/rs/0"))
                .andExpect(jsonPath("$.error", is("invalid index")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_400_and_message_when_start_end_not_right() throws Exception {
        mockMvc.perform(get("/rs/listBetween?start=0&end=2"))
                .andExpect(jsonPath("$.error", is("invalid request param")))
                .andExpect(status().isBadRequest());
    }


    @AfterEach
    void tearDown() {
        rsEventRepository.deleteAll();
        userRepository.deleteAll();
        rsEventRepository.resetAutoIncrement();
        userRepository.resetAutoIncrement();
    }
}
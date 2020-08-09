package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by wzw on 2020/8/5.
 */
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RsEventRepository rsEventRepository;
    @Autowired
    private UserRepository userRepository;
    UserDto userDto;
    RsEventDto rsEventDto;
    @BeforeEach
    void setUp() {
        userDto = UserDto.builder().email("wzw@qq.com").gender("male")
                .age(22).phone("18888888888").userName("wzw").voteNum(10).build();
        userDto = userRepository.save(userDto);
        rsEventDto = RsEventDto.builder().keyWord("经济").eventName("大爆炸").userDto(userDto).build();
        rsEventDto = rsEventRepository.save(rsEventDto);
    }

    @Test
    void should_add_user() throws Exception {
        User user = new User("wzw", "male", 22, "wzw@qq.com", "18888888888", 5);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void should_return_400_when_name_too_long() throws Exception {
        User user = new User("wzwasdasdad", "male", 22, "wzw@qq.com", "18888888888", 5);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_400_when_gender_is_null() throws Exception {
        User user = new User("wzw", null, 22, "wzw@qq.com", "18888888888", 5);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_400_when_age_less_than_18() throws Exception {
        User user = new User("wzw", "male", 15, "wzw@qq.com", "18888888888", 5);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_400_when_age_more_than_100() throws Exception {
        User user = new User("wzw", "male", 101, "wzw@qq.com", "18888888888", 5);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_400_when_email_is_wrong() throws Exception {
        User user = new User("wzw", "male", 22, "wzwqq.com", "18888888888", 5);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_400_when_phone_too_long() throws Exception {
        User user = new User("wzw", "male", 22, "wzw@qq.com", "188888888888", 5);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_400_when_phone_not_begin_with_1() throws Exception {
        User user = new User("wzw", "male", 22, "wzw@qq.com", "28888888888", 5);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_user_list() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(jsonPath("$[0].name", is("wzw")))
                .andExpect(jsonPath("$[0].gender", is("male")))
                .andExpect(jsonPath("$[0].age", is(22)))
                .andExpect(jsonPath("$[0].email", is("wzw@qq.com")))
                .andExpect(jsonPath("$[0].phone", is("18888888888")))
                .andExpect(status().isOk());
    }

    @Test
    void should_return_400_and_message_when_user_not_pass_valid() throws Exception {
        User user = new User("wzw", "male", 22, "wzw@qq.com", "28888888888", 5);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("invalid user")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_user_message_by_user_id() throws Exception {
        mockMvc.perform(get("/user/1"))
                .andExpect(jsonPath("$.name", is("wzw")))
                .andExpect(jsonPath("$.gender", is("male")))
                .andExpect(jsonPath("$.age", is(22)))
                .andExpect(jsonPath("$.email", is("wzw@qq.com")))
                .andExpect(jsonPath("$.phone", is("18888888888")))
                .andExpect(jsonPath("$.voteNum", is(10)))
                .andExpect(status().isOk());
    }

    @Test
    void should_delete_user_by_user_id() throws Exception {
        mockMvc.perform(delete("/user/delete/1"))
                .andExpect(status().isOk());
        assertEquals(null, rsEventRepository.findByUserDtoId(1));
    }
    @AfterEach
    void tearDown() {
        rsEventRepository.deleteAll();
        userRepository.deleteAll();
        rsEventRepository.resetAutoIncrement();
        userRepository.resetAutoIncrement();
    }
}
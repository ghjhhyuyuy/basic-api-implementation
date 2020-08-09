package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.api.RsController;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import com.thoughtworks.rslist.service.impl.RsServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by wzw on 2020/8/9.
 */
class RsServiceImplTest {
    RsService rsService;
    @Mock
    UserRepository userRepository;
    @Mock
    RsEventRepository rsEventRepository;
    @BeforeEach
    void setUp() {
        initMocks(this);
        rsService = new RsServiceImpl(userRepository,rsEventRepository);
    }
    @Test
    void get_rs_event_number() throws Exception {
        UserDto userDto = UserDto.builder().email("wzw@qq.com").gender("male")
                .age(22).phone("18888888888").userName("wzw").voteNum(10).id(1).build();
        RsEventDto rsEventDto = RsEventDto.builder().eventName("hahaha").keyWord("haha")
                .id(anyInt()).userDto(userDto).build();
        Optional<RsEventDto> rsEventDtoOptional = Optional.of(rsEventDto);
        when(rsEventRepository.findById(-1)).thenReturn(Optional.empty());
        when(rsEventRepository.findById(1)).thenReturn(rsEventDtoOptional);

        assertEquals(rsEventDto,rsService.rsListIndex(1));
        Throwable throwable = assertThrows(InvalidIndexException.class, ()->rsService.rsListIndex(-1));
        assertEquals("invalid index",throwable.getMessage());
    }
    @Test
    void get_rs_event_between() throws Exception {
        UserDto userDto = UserDto.builder().email("wzw@qq.com").gender("male")
                .age(22).phone("18888888888").userName("wzw").voteNum(10).id(1).build();
        RsEventDto rsEventDto = RsEventDto.builder().eventName("hahaha").keyWord("haha")
                .id(1).userDto(userDto).build();
        List<RsEventDto> rsEventDtoList = new ArrayList<>();
        rsEventDtoList.add(rsEventDto);
        when(rsEventRepository.findAll()).thenReturn(rsEventDtoList);
        assertEquals(rsEventDtoList,rsService.rsListBetween(1,1));
        Throwable throwable = assertThrows(InvalidIndexException.class, ()->rsService.rsListBetween(1,4));
        assertEquals("invalid request param",throwable.getMessage());
    }
    @Test
    void update_rs_event() throws Exception {
        UserDto userDto = UserDto.builder().email("wzw@qq.com").gender("male")
                .age(22).phone("18888888888").userName("wzw").voteNum(10).id(1).build();
        RsEventDto rsEventDto = RsEventDto.builder().eventName("hahaha").keyWord("haha")
                .id(1).userDto(userDto).build();
        RsEventDto rsSuccessEventDto = RsEventDto.builder().eventName("haha").keyWord("haha")
                .id(1).userDto(userDto).build();
        RsEvent updateRsEvent = new RsEvent("haha","haha",1);
        RsEvent updateFailRsEvent = new RsEvent(null,null,1);
        Optional<RsEventDto> rsEventDtoOptional = Optional.of(rsEventDto);
        when(rsEventRepository.findById(-1)).thenReturn(Optional.empty());
        when(rsEventRepository.findById(1)).thenReturn(rsEventDtoOptional);
        when(rsEventRepository.save(rsSuccessEventDto)).thenReturn(rsSuccessEventDto);
        assertEquals(rsSuccessEventDto,rsService.update(1, updateRsEvent));
        Throwable throwable = assertThrows(Exception.class, ()-> rsService.update(-1,updateRsEvent));
        assertEquals("invalid param",throwable.getMessage());
        Throwable throwable1 = assertThrows(Exception.class, ()-> rsService.update(1,updateFailRsEvent));
        assertEquals("input error",throwable1.getMessage());

    }
    @Test
    void add_rs_event() throws Exception {
        UserDto userDto = UserDto.builder().email("wzw@qq.com").gender("male")
                .age(22).phone("18888888888").userName("wzw").voteNum(10).id(1).build();
        RsEventDto rsEventDto = RsEventDto.builder().eventName("hahaha").keyWord("haha")
                .id(1).userDto(userDto).build();
        RsEventDto rsEventDtoOrigin = RsEventDto.builder().eventName("hahaha").keyWord("haha")
                .id(0).userDto(userDto).build();
        RsEvent rsEvent = new RsEvent("haha","haha",-1);
        RsEvent rsEventSuccess = new RsEvent("hahaha","haha",1);
        when(userRepository.findById(-1)).thenReturn(Optional.empty());
        when(userRepository.findById(1)).thenReturn(Optional.of(userDto));
        when(rsEventRepository.save(rsEventDtoOrigin)).thenReturn(rsEventDto);
        assertEquals(rsEventDto,rsService.rsEvent(rsEventSuccess));
        Throwable throwable = assertThrows(InvalidIndexException.class, ()->rsService.rsEvent(rsEvent));
        assertEquals("invalid param",throwable.getMessage());
    }
}
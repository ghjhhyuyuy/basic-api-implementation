package com.thoughtworks.rslist.service.impl;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.service.RsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by wzw on 2020/8/9.
 */
@Service
public class RsServiceImpl implements RsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RsEventRepository rsEventRepository;
    @Override
    public List<RsEventDto> rsList() {
        return rsEventRepository.findAll();
    }

    @Override
    public RsEventDto rsListIndex(int index) throws InvalidIndexException {
        Optional<RsEventDto> rsEventDtoOptional = rsEventRepository.findById(index);
        if (rsEventDtoOptional.isPresent()) {
            return rsEventDtoOptional.get();
        } else {
            throw new InvalidIndexException("invalid index");
        }
    }

    @Override
    public List<RsEventDto> rsListBetween(int start, int end) throws InvalidIndexException {
        List<RsEventDto> rsList = rsEventRepository.findAll();
        if (start <= 0 || end > rsList.size() || start > end) {
            throw new InvalidIndexException("invalid request param");
        }else {
            return rsList.subList(start - 1, end);
        }
    }

    @Override
    public boolean rsEvent(RsEvent rsEvent) {
        int userId = rsEvent.getUserId();
        Optional<UserDto> optionalUserDto = userRepository.findById(userId);
        if (optionalUserDto.isPresent()) {
            RsEventDto rsEventDto = RsEventDto.builder().eventName(rsEvent.getEventName()).keyWord(rsEvent.getKeyWords()).userDto(optionalUserDto.get()).build();
            rsEventRepository.save(rsEventDto);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(int rsEventId, RsEvent rsEvent) throws Exception {
        Optional<RsEventDto> rsEventDtoOptional = rsEventRepository.findById(rsEventId);
        RsEventDto rsEventDto = new RsEventDto();
        if (rsEventDtoOptional.isPresent()) {
            rsEventDto = rsEventDtoOptional.get();
        } else {
            return false;
        }
        if (rsEventDto.getUserDto().getId() == rsEvent.getUserId()) {
            if (rsEvent.getEventName() == null && rsEvent.getKeyWords() == null) {
                throw new Exception("input error");
            }
            if (rsEvent.getEventName() != null && rsEvent.getKeyWords() != null) {
                rsEventDto.setKeyWord(rsEvent.getKeyWords());
                rsEventDto.setEventName(rsEvent.getEventName());
            } else if (rsEvent.getKeyWords() == null) {
                rsEventDto.setEventName(rsEvent.getEventName());
            } else {
                rsEventDto.setKeyWord(rsEvent.getKeyWords());
            }
            rsEventRepository.save(rsEventDto);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int index) {
        rsEventRepository.deleteById(index);
        return true;
    }
}

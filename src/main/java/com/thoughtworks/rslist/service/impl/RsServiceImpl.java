package com.thoughtworks.rslist.service.impl;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.service.RsService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public RsServiceImpl(UserRepository userRepository, RsEventRepository rsEventRepository) {
        this.userRepository = userRepository;
        this.rsEventRepository = rsEventRepository;
    }

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
        } else {
            return rsList.subList(start - 1, end);
        }
    }

    @Override
    public RsEventDto rsEvent(RsEvent rsEvent) throws InvalidIndexException {
        int userId = rsEvent.getUserId();
        Optional<UserDto> optionalUserDto = userRepository.findById(userId);
        if (optionalUserDto.isPresent()) {
            RsEventDto rsEventDto = RsEventDto.builder().eventName(rsEvent.getEventName()).keyWord(rsEvent.getKeyWords()).userDto(optionalUserDto.get()).build();
            return rsEventRepository.save(rsEventDto);
        }else {
            throw new InvalidIndexException("invalid param");
        }
    }

    @Override
    public RsEventDto update(int rsEventId, RsEvent rsEvent) throws Exception {
        Optional<RsEventDto> rsEventDtoOptional = rsEventRepository.findById(rsEventId);
        RsEventDto rsEventDto = new RsEventDto();
        if (rsEventDtoOptional.isPresent()) {
            rsEventDto = rsEventDtoOptional.get();
        } else {
            throw new InvalidIndexException("invalid param");
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
            return rsEventRepository.save(rsEventDto);
        }
        throw new InvalidIndexException("invalid userId");
    }

    @Override
    public void delete(int index) throws InvalidIndexException {
        try {
            rsEventRepository.deleteById(index);
        }catch (Exception e){
            throw new InvalidIndexException("invalid param");
        }
    }
}

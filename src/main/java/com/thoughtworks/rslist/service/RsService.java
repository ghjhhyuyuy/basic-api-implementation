package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wzw on 2020/8/9.
 */
public interface RsService {
    public List<RsEventDto> rsList();

    public RsEventDto rsListIndex(int index) throws InvalidIndexException;

    public List<RsEventDto> rsListBetween(int start, int end) throws InvalidIndexException;

    public boolean rsEvent(RsEvent rsEvent);

    public boolean update(int rsEventId, RsEvent rsEvent) throws Exception;

    public boolean delete(int index);
}

package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.exception.InvalidIndexException;

import java.util.List;

/**
 * Created by wzw on 2020/8/9.
 */
public interface RsService {
    public List<RsEventDto> rsList();

    public RsEventDto rsListIndex(int index) throws InvalidIndexException;

    public List<RsEventDto> rsListBetween(int start, int end) throws InvalidIndexException;

    public RsEventDto rsEvent(RsEvent rsEvent) throws InvalidIndexException;

    public RsEventDto update(int rsEventId, RsEvent rsEvent) throws Exception;

    public void delete(int index) throws InvalidIndexException;
}

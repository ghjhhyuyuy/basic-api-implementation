package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by wzw on 2020/8/4.
 */
@Data
public class RsEvent {
    @NotNull
    String eventName;
    @NotNull
    String keyWords;
    @NotNull
    int userId;

    public RsEvent(@NotNull String eventName, @NotNull String keyWords, @NotNull @Valid int userId) {
        this.eventName = eventName;
        this.keyWords = keyWords;
        this.userId = userId;
    }
}
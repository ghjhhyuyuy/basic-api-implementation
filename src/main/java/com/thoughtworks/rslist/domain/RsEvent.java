package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by wzw on 2020/8/4.
 */
public class RsEvent {
    @NotNull
    String eventName;
    @NotNull
    String keyWords;
    @NotNull
    User user;

    public RsEvent() {
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public RsEvent(String eventName, String keyWords, User user) {
        this.eventName = eventName;
        this.keyWords = keyWords;
        this.user = user;
    }
    @JsonIgnore
    public User getUser() {
        return user;
    }
    @JsonProperty
    public void setUser(User user) {
        this.user = user;
    }
}

package com.thoughtworks.rslist.domain;

/**
 * Created by wzw on 2020/8/4.
 */
public class RsEvent {
    String eventName;
    String keyWords;

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

    public RsEvent(String eventName, String keyWords) {
        this.eventName = eventName;
        this.keyWords = keyWords;
    }
}

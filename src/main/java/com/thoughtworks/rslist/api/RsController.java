package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.service.RsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class RsController {
    @Autowired
    private RsService rsService;

    @GetMapping("/rs/list")
    @ResponseBody
    public ResponseEntity rsList() {
        List<RsEventDto> rsList = rsService.rsList();
        return ResponseEntity.ok(rsList);
    }

    @GetMapping("/rs/{index}")
    @ResponseBody
    public ResponseEntity rsListIndex(@PathVariable int index) throws InvalidIndexException {
        return ResponseEntity.ok(rsService.rsListIndex(index));
    }

    @GetMapping("/rs/listBetween")
    @ResponseBody
    public ResponseEntity rsListBetween(@RequestParam int start, @RequestParam int end) throws InvalidIndexException {
        return ResponseEntity.ok(rsService.rsListBetween(start, end));
    }

    @PostMapping("/rs/event")
    public ResponseEntity rsEvent(@RequestBody @Valid RsEvent rsEvent) throws InvalidIndexException {
        return ResponseEntity.created(null).body(rsService.rsEvent(rsEvent));
    }

    @PatchMapping(value = "/rs/{rsEventId}")
    public ResponseEntity update(@PathVariable int rsEventId, @RequestParam(required = false) String eventName, @RequestParam(required = false) String keyWords, @RequestParam int userId) throws Exception {
        RsEvent rsEvent = new RsEvent(eventName, keyWords, userId);
        RsEventDto rsEventDto = rsService.update(rsEventId, rsEvent);
        return ResponseEntity.ok(rsEventDto);
    }

    @DeleteMapping("/rs/delete/{index}")
    public ResponseEntity delete(@PathVariable int index) throws InvalidIndexException {
        rsService.delete(index);
        return ResponseEntity.ok().build();
    }
}

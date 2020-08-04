package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {
  private List<RsEvent> rsList = new ArrayList<>();
  RsController(){
    RsEvent rsEvent = new RsEvent("第一条事件","无标签");
    RsEvent rsEvent1 = new RsEvent("第二条事件","无标签");
    RsEvent rsEvent2 = new RsEvent("第三条事件","无标签");
    rsList.add(rsEvent);
    rsList.add(rsEvent1);
    rsList.add(rsEvent2);
  }
  @GetMapping("/rs/list")
  @ResponseBody
  public List<RsEvent> rsList(){
    return rsList;
  }
  @GetMapping("/rs/{index}")
  @ResponseBody
  public RsEvent rsListIndex(@PathVariable int index){
    return rsList.get(index - 1);
  }
  @GetMapping("/rs/listBetween")
  @ResponseBody
  public List<RsEvent> rsListBetween(@RequestParam int start,@RequestParam int end){
    return rsList.subList(start - 1,end);
  }
  @PostMapping("/rs/event")
  public void rsEvent(@RequestBody RsEvent rsEvent){
    rsList.add(rsEvent);
  }
}

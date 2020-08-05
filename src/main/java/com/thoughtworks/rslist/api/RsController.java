package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
  @Autowired
  private UserController userController;
  private List<RsEvent> rsList = new ArrayList<>();

  RsController() {
    RsEvent rsEvent = new RsEvent("第一条事件", "无标签",new User("wzw","male",22,"wzw@qq.com","18888888888"));
    RsEvent rsEvent1 = new RsEvent("第二条事件", "无标签",new User("wzw","male",22,"wzw@qq.com","18888888888"));
    RsEvent rsEvent2 = new RsEvent("第三条事件", "无标签",new User("wzw","male",22,"wzw@qq.com","18888888888"));
    rsList.add(rsEvent);
    rsList.add(rsEvent1);
    rsList.add(rsEvent2);
  }

  @GetMapping("/rs/list")
  @ResponseBody
  public List<RsEvent> rsList() {
    return rsList;
  }

  @GetMapping("/rs/{index}")
  @ResponseBody
  public RsEvent rsListIndex(@PathVariable int index) {
    return rsList.get(index - 1);
  }

  @GetMapping("/rs/listBetween")
  @ResponseBody
  public List<RsEvent> rsListBetween(@RequestParam int start, @RequestParam int end) {
    return rsList.subList(start - 1, end);
  }

  @PostMapping("/rs/event")
  public void rsEvent(@RequestBody RsEvent rsEvent) {
    User user = rsEvent.getUser();
    userController.addUser(user);
    rsList.add(rsEvent);
  }

  @RequestMapping(value = "/rs/update/{index}", method = RequestMethod.PATCH)
  public void update(@PathVariable int index, @RequestParam(required = false) String eventName, @RequestParam(required = false) String keyWords) throws Exception {
    RsEvent rsEvent = rsList.get(index - 1);
    if (eventName == null && keyWords == null) {
      throw new Exception("input error");
    }
    if (eventName != null && keyWords != null) {
      rsEvent.setKeyWords(keyWords);
      rsEvent.setEventName(eventName);
    } else if (keyWords == null) {
      rsEvent.setEventName(eventName);
    } else {
      rsEvent.setKeyWords(keyWords);
    }
    rsList.set(index - 1, rsEvent);
  }

  @DeleteMapping("/rs/delete/{index}")
  public void delete(@PathVariable int index) {
    rsList.remove(index - 1);
  }
}

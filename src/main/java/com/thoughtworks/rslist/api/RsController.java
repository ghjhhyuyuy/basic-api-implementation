package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.exception.Error;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class RsController {
  @Autowired
  private UserController userController;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RsEventRepository rsEventRepository;
  private List<RsEvent> rsList = new ArrayList<>();

  RsController() {
    RsEvent rsEvent = new RsEvent("第一条事件", "无标签",1);
    RsEvent rsEvent1 = new RsEvent("第二条事件", "无标签",1);
    RsEvent rsEvent2 = new RsEvent("第三条事件", "无标签",1);
    rsList.add(rsEvent);
    rsList.add(rsEvent1);
    rsList.add(rsEvent2);
  }

  @GetMapping("/rs/list")
  @ResponseBody
  public ResponseEntity rsList() {
    return ResponseEntity.ok(rsList);
  }

  @GetMapping("/rs/{index}")
  @ResponseBody
  public ResponseEntity rsListIndex(@PathVariable int index) throws InvalidIndexException {
    if(index <= 0 || index > rsList.size()){
      throw new InvalidIndexException("invalid index");
    }
    return ResponseEntity.ok(rsList.get(index - 1));
  }

  @GetMapping("/rs/listBetween")
  @ResponseBody
  public ResponseEntity rsListBetween(@RequestParam int start, @RequestParam int end) throws InvalidIndexException {
    if(start <=0 || end > rsList.size() || start > end){
      throw new InvalidIndexException("invalid request param");
    }
    return ResponseEntity.ok(rsList.subList(start - 1, end));
  }

  @PostMapping("/rs/event")
  public ResponseEntity rsEvent(@RequestBody @Valid RsEvent rsEvent) {
    int userId = rsEvent.getUserId();
    Optional<UserDto> optionalUserDto = userRepository.findById(userId);
    if(optionalUserDto.isPresent()){
      RsEventDto rsEventDto = RsEventDto.builder().eventName(rsEvent.getEventName()).keyWord(rsEvent.getKeyWords()).userDto(optionalUserDto.get()).build();
      rsEventRepository.save(rsEventDto);
      return ResponseEntity.created(null).build();
    }
    return ResponseEntity.badRequest().build();
  }

  @RequestMapping(value = "/rs/update/{index}", method = RequestMethod.PATCH)
  public ResponseEntity update(@PathVariable int index, @RequestParam(required = false) String eventName, @RequestParam(required = false) String keyWords) throws Exception {
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
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/rs/delete/{index}")
  public ResponseEntity delete(@PathVariable int index) {
    rsList.remove(index - 1);
    return ResponseEntity.ok().build();
  }
  private static void createTableByJdbc() throws SQLException {
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/rsSystem","","");
    DatabaseMetaData databaseMetaData = connection.getMetaData();
    ResultSet resultSet = databaseMetaData.getTables(null,null,"rsEvent",null);
    if(!resultSet.next()){
      String createTableSql = "create table rsEvent(eventName varchar(200) not null keyword varchar(100) not null";
      Statement statement = connection.createStatement();
      statement.execute(createTableSql);
    }
    connection.close();
  }
}

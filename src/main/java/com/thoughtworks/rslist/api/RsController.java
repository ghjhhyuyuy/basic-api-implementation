package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.*;
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

    @GetMapping("/rs/list")
    @ResponseBody
    public ResponseEntity rsList() {
        List<RsEventDto> rsList = rsEventRepository.findAll();
        return ResponseEntity.ok(rsList);
    }

    @GetMapping("/rs/{index}")
    @ResponseBody
    public ResponseEntity rsListIndex(@PathVariable int index) throws InvalidIndexException {
        Optional<RsEventDto> rsEventDtoOptional = rsEventRepository.findById(index);
        if (rsEventDtoOptional.isPresent()) {
            return ResponseEntity.ok(rsEventDtoOptional.get());
        } else {
            throw new InvalidIndexException("invalid index");
        }

    }

    @GetMapping("/rs/listBetween")
    @ResponseBody
    public ResponseEntity rsListBetween(@RequestParam int start, @RequestParam int end) throws InvalidIndexException {
        List<RsEventDto> rsList = rsEventRepository.findAll();
        if (start <= 0 || end > rsList.size() || start > end) {
            throw new InvalidIndexException("invalid request param");
        }
        return ResponseEntity.ok(rsList.subList(start - 1, end));
    }

    @PostMapping("/rs/event")
    public ResponseEntity rsEvent(@RequestBody @Valid RsEvent rsEvent) {
        int userId = rsEvent.getUserId();
        Optional<UserDto> optionalUserDto = userRepository.findById(userId);
        if (optionalUserDto.isPresent()) {
            RsEventDto rsEventDto = RsEventDto.builder().eventName(rsEvent.getEventName()).keyWord(rsEvent.getKeyWords()).userDto(optionalUserDto.get()).build();
            rsEventRepository.save(rsEventDto);
            return ResponseEntity.created(null).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PatchMapping(value = "/rs/{rsEventId}")
    public ResponseEntity update(@PathVariable int rsEventId, @RequestParam(required = false) String eventName, @RequestParam(required = false) String keyWords, @RequestParam int userId) throws Exception {
        Optional<RsEventDto> rsEventDtoOptional = rsEventRepository.findById(rsEventId);
        RsEventDto rsEventDto = new RsEventDto();
        if (rsEventDtoOptional.isPresent()) {
            rsEventDto = rsEventDtoOptional.get();
        } else {
            return ResponseEntity.badRequest().build();
        }
        if (rsEventDto.getUserDto().getId() == userId) {
            if (eventName == null && keyWords == null) {
                throw new Exception("input error");
            }
            if (eventName != null && keyWords != null) {
                rsEventDto.setKeyWord(keyWords);
                rsEventDto.setEventName(eventName);
            } else if (keyWords == null) {
                rsEventDto.setEventName(eventName);
            } else {
                rsEventDto.setKeyWord(keyWords);
            }
            rsEventRepository.save(rsEventDto);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/rs/delete/{index}")
    public ResponseEntity delete(@PathVariable int index) {
        RsEventDto rsEventDto = new RsEventDto();
        if (rsEventRepository.findById(index).isPresent()) {
            rsEventDto = rsEventRepository.findById(index).get();
        }
        rsEventRepository.delete(rsEventDto);
        return ResponseEntity.ok().build();
    }

    private static void createTableByJdbc() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/rsSystem", "", "");
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet resultSet = databaseMetaData.getTables(null, null, "rsEvent", null);
        if (!resultSet.next()) {
            String createTableSql = "create table rsEvent(eventName varchar(200) not null keyword varchar(100) not null";
            Statement statement = connection.createStatement();
            statement.execute(createTableSql);
        }
        connection.close();
    }
}

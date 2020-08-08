package com.thoughtworks.rslist.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by wzw on 2020/8/6.
 */
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Table(name = "rsEventDto")
public class RsEventDto {
    @Id
    @GeneratedValue
    private int id;
    private String eventName;
    private String keyWord;
    @ManyToOne
    @JsonIgnore
    private UserDto userDto;
}

package com.thoughtworks.rslist.domain;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * Created by wzw on 2020/8/5.
 */
@Data
public class User {
    @NotNull
    @Size(max = 8)
    String name;
    @NotNull
    String gender;
    @Min(18)
    @Max(100)
    int age;
    @Email
    String email;
    @Pattern(regexp = "^1[0-9]{10}")
    String phone;
    int voteNum;

    public User(@NotNull @Size(max = 8) String name, @NotNull String gender, @Min(18) @Max(100) int age, @Email String email, @Pattern(regexp = "^1[0-9]{10}") String phone, int voteNum) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.voteNum = voteNum;
    }
}

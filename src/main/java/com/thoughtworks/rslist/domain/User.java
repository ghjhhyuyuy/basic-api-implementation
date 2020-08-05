package com.thoughtworks.rslist.domain;

import javax.validation.constraints.*;

/**
 * Created by wzw on 2020/8/5.
 */
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

    public User(String name, String gender, int age, String email, String phone) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

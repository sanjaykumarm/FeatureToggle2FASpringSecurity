package com.dange.tanmay;

import com.dange.tanmay.dao.User;

import java.util.List;

public class UsersDto {
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

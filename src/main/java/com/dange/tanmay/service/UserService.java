package com.dange.tanmay.service;

import com.dange.tanmay.dao.User;
import com.dange.tanmay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public List<User> getAllUsers()
    {
        List<User> users = new ArrayList<>();
        repository.findAll().forEach(user -> users.add(user));
        return users;
    }

    public User getUserById(int id)
    {
        return repository.findById(id).get();
    }

    public void saveOrUpdate(User user)
    {
        repository.save(user);
    }

    public void forceEnableMFA(String username, boolean forceEnabled)
    {
        repository.forceEnableMFA(forceEnabled, username);
    }

    //deleting a specific record
    public String delete(int id){
        repository.deleteById(id);
        return  "Successfully Deleted!";
    }


    public User getUserByUsername(String username)
    {
        List<User> userList = (List<User>) repository.findAll();

        for (User user : userList){
            if (user.username.equals(username))
                {return user;}
        }
        return null;
    }
}

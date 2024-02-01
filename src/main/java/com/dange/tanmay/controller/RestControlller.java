package com.dange.tanmay.controller;

import com.dange.tanmay.dao.User;
import com.dange.tanmay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class RestControlller {

    @Autowired
    UserService userService;

    String DEFAULT_NAME = "tanmay";
    boolean newFeatures=false;

    @GetMapping(path = "/example/v1/greet")
    public String get(){
        return "Hello";
    }


    @GetMapping(path = "/")
    public String getHome(){
        return "<h1>Welcome</h1>";
    }

    @GetMapping(path = "/example/v2/greet/{name}")
    public String getV2(@PathVariable String name){
        return "Hello "+ name;
    }

    @GetMapping(path = "/example/v2/greet")
    public String getV21() {
        return "Hello "+ DEFAULT_NAME;
    }



    @GetMapping(path = "/example/about")
    public String getAbout(){
        return getInfo();
    }


    @GetMapping(path = "/example/enableNewFeatures")
    public String setEnablNewFeatures(){
        newFeatures=true;
        return "New Features Enabled";
    }

    @GetMapping(path = "/example/disableNewFeatures")
    public String setDisableNewFeatures(){
        newFeatures=false;
        return "New Features Disabled";
    }

    @PutMapping(path = "/example/forceEnableMFA")
    public String forceEnableMFA(@RequestBody ArrayList<User> users) {
        for (User user: users) {
            userService.forceEnableMFA(user.username, user.forceEnabled);
        }
        return "Users updated successfully.";
    }



    public String getInfo(){
        if(newFeatures){
            return "This the about section. There are lot of new features are that have been added to system";
        }else{
            return "This the about section.";
        }

    }
}

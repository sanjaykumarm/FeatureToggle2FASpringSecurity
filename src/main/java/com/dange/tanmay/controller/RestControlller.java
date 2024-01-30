package com.dange.tanmay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControlller {

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



    public String getInfo(){
        if(newFeatures){
            return "This the about section. There are lot of new features are that have been added to system";
        }else{
            return "This the about section.";
        }

    }
}

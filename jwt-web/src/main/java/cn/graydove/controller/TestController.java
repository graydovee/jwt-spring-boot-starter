package cn.graydove.controller;

import cn.graydove.security.annotation.Authorization;
import cn.graydove.security.annotation.CurrentUser;
import cn.graydove.security.userdetails.support.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @PostMapping("/register")
    public User register(@CurrentUser User user){
        return user;
    }

    @PostMapping("/info")
    public User getInfo(@CurrentUser User user){
        return user;
    }

    @PostMapping("/post")
    public String post() {
        return "post";
    }

    @Authorization(permitAll = true)
    @GetMapping("/get")
    public String get(){
        return "Get";
    }

    @Authorization
    @DeleteMapping("/delete")
    public String delete(){
        return "delete";
    }

}

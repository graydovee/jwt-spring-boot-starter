package cn.graydove.controller;

import cn.graydove.security.annotation.CurrentUser;
import cn.graydove.security.userdetails.support.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/post")
    public String post() {
        return "post";
    }

}

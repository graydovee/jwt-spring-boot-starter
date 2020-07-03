package cn.graydove.controller;

import cn.graydove.security.annotation.Authorization;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Authorization(permitAll = true)
public class Test3Controller {

    @RequestMapping("/put3")
    public String put3(){
        return "put3";
    }


    @Authorization("USER")
    @PutMapping("/put5")
    public String put5(){
        return "put5";
    }
}

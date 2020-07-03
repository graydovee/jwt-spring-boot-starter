package cn.graydove.controller;

import cn.graydove.security.annotation.Authorization;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class Test2Controller {

    @Authorization(permitAll = true)
    @PutMapping("/put")
    public String put(){
        return "put";
    }


    @Authorization("deny")
    @PutMapping("/put2")
    public String put2(){
        return "put2";
    }
}

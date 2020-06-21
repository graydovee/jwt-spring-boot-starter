package cn.graydove;

import cn.graydove.security.annotation.CurrentUser;
import cn.graydove.security.userdetails.support.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application {
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    @PostMapping("/register")
    public String register(@CurrentUser User user){
        return "success";
    }

    @PostMapping("/info")
    public User getInfo(@CurrentUser User user){
        return user;
    }

}

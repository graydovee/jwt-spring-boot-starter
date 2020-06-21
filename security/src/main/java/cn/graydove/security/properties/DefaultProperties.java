package cn.graydove.security.properties;

import lombok.Data;

@Data
public class DefaultProperties {

    private String username = "username";

    private String password = "password";

    private String authorities = "user";
}

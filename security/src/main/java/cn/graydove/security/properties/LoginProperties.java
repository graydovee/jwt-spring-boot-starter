package cn.graydove.security.properties;

import lombok.Data;

@Data
public class LoginProperties {

    private String loginUri = "/login";

    private String method = "POST";

    private String usernameKeyName = "username";

    private String passwordKeyName = "password";
}

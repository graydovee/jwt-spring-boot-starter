package cn.graydove.security.userdetails;

import java.util.List;

public interface UserDetails {
    String USER_PARAM_NAME = "cn.graydove.security.userdetails";

    String getUsername();

    String getPassword();

    List<? extends GrantedAuthority> getAuthorities();

    boolean isEnable();

    void setPassword(String password);
}

package cn.graydove.security.userdetails.support;

import cn.graydove.security.userdetails.GrantedAuthority;
import cn.graydove.security.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    private String username;

    private String password;

    private List<Authority> authorities;

    private boolean enable = true;

    @Override
    @JsonIgnore
    public boolean isEnable() {
        return enable;
    }
}

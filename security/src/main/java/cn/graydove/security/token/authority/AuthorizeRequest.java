package cn.graydove.security.token.authority;

import cn.graydove.security.userdetails.GrantedAuthority;
import cn.graydove.security.userdetails.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizeRequest {
    private String pattern;
    private List<String> authorities = new ArrayList<>();

    private AuthorityAssert authorityAssert;

    public boolean asserts(UserDetails userDetails) {
        return authorityAssert.asserts(authorities, userDetails);
    }
}

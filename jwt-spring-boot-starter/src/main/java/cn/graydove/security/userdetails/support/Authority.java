package cn.graydove.security.userdetails.support;

import cn.graydove.security.userdetails.GrantedAuthority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority implements GrantedAuthority {

    private String authority;

    public static List<Authority> getAuthoritiesByString(String authorities) {
        List<Authority> list = new ArrayList<>();
        if (StringUtils.isEmpty(authorities)) {
            return list;
        }
        String[] split = authorities.split(",");
        for (String s: split) {
            list.add(new Authority(s.trim()));
        }
        return list;
    }
}

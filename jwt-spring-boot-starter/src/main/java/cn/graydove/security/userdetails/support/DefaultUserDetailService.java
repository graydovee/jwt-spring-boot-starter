package cn.graydove.security.userdetails.support;

import cn.graydove.security.exception.UsernameNotFoundException;
import cn.graydove.security.userdetails.UserDetailService;
import org.springframework.beans.BeanUtils;

public class DefaultUserDetailService implements UserDetailService<User> {

    private User user;

    public DefaultUserDetailService(User user) {
        this.user = user;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        BeanUtils.copyProperties(this.user, user);
        return user;
    }
}

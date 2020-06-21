package cn.graydove.security.userdetails;

import cn.graydove.security.exception.UsernameNotFoundException;

public interface UserDetailService<T extends UserDetails> {

    T loadUserByUsername(String username) throws UsernameNotFoundException;
}

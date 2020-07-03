package cn.graydove.security.crypto.support;

import cn.graydove.security.crypto.PasswordEncoder;
import org.apache.commons.lang3.StringUtils;

public class PlainPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return StringUtils.equals(rawPassword, encodedPassword);
    }
}

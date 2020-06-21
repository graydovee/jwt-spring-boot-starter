package cn.graydove.security.exception;

public class UsernameNotFoundException extends FailException {
    public UsernameNotFoundException() {
        super("用户不存在");
    }
}

package cn.graydove.security.exception;

public class UserNotEnableException extends FailException {
    public UserNotEnableException() {
        super("用户不可用");
    }
}

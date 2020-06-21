package cn.graydove.security.exception;

public class PasswordNotMatchException extends FailException {

    public PasswordNotMatchException() {
        super("密码错误");
    }
}

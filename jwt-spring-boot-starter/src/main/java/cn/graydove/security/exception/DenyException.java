package cn.graydove.security.exception;

public class DenyException extends FailException {
    public DenyException() {
        super("无权限访问");
    }
}

package cn.graydove.security.exception;

public class InvalidTokenException extends FailException {
    public InvalidTokenException() {
        super("无效令牌");
    }
}

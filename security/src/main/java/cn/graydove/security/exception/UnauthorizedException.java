package cn.graydove.security.exception;

public class UnauthorizedException extends FailException {
    public UnauthorizedException() {
        super("请先登录");
    }
}

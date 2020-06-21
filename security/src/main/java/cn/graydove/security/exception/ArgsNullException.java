package cn.graydove.security.exception;

public class ArgsNullException extends FailException {
    public ArgsNullException() {
        super("参数为空");
    }
}

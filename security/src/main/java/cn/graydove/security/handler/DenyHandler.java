package cn.graydove.security.handler;

public abstract class DenyHandler extends AbstractFailHandler {
    @Override
    public int getCode() {
        return 403;
    }
}

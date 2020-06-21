package cn.graydove.security.handler;


public abstract class UnauthorizedHandler extends AbstractFailHandler {


    @Override
    public int getCode() {
        return 401;
    }
}

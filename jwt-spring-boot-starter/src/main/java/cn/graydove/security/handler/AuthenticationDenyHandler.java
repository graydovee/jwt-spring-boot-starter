package cn.graydove.security.handler;


public abstract class AuthenticationDenyHandler extends AbstractFailHandler {

    @Override
    public int getCode() {
        return 403;
    }
}

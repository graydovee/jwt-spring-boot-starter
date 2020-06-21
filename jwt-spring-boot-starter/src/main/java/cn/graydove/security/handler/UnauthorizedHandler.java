package cn.graydove.security.handler;


/**
 * 未登录
 */
public abstract class UnauthorizedHandler extends AbstractFailHandler {


    @Override
    public int getCode() {
        return 401;
    }
}

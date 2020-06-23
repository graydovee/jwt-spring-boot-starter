package cn.graydove.security.token;

import cn.graydove.security.exception.NotSupportException;

public interface TokenSupportAssert {
    Iterable<String> getSupportTypes();

    default void assertSupport(String type) {
        for (String supportType: getSupportTypes()) {
            if (supportType.equals(type)) {
                return;
            }
        }
        throw new NotSupportException("支持该类型的Token，请使用合适的TokenGetter");
    }
}

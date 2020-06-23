package cn.graydove.security.token.getter;

import cn.graydove.security.properties.TokenProperties;
import java.util.Collections;

public interface BearerTokenGetter extends TokenGetter {

    @Override
    default Iterable<String> getSupportTypes() {
        return Collections.singletonList(TokenProperties.TYPE_BEARER);
    }
}

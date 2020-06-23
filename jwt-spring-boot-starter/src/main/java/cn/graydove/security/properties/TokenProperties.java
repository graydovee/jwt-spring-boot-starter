package cn.graydove.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Data
public class TokenProperties {

    public final static String TYPE_BEARER = "Bearer";

    private String secret = "gray_dove";

    private Map<String, Object> claims = Collections.emptyMap();

    private String issuer = "http://";

    private String type = TYPE_BEARER;

    private String tokenKey = "Authorization";

    private long ttl = 60 * 60 * 1000;
}

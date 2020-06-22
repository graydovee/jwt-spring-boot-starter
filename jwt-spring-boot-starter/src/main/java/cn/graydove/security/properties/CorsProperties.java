package cn.graydove.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("jwt.cors")
public class CorsProperties {

    private boolean enable = true;

    private String origin = "*";

    private String header = "*";

    private String method = "*";

    private String path = "/**";
}

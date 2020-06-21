package cn.graydove.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private boolean enable = true;

    @NestedConfigurationProperty
    private CorsProperties cors = new CorsProperties();

    @NestedConfigurationProperty
    private DefaultProperties defaults = new DefaultProperties();

    @NestedConfigurationProperty
    private LoginProperties login = new LoginProperties();

    @NestedConfigurationProperty
    private TokenProperties token = new TokenProperties();
}

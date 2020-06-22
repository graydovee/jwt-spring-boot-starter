package cn.graydove.security.config;

import cn.graydove.security.properties.CorsProperties;
import cn.graydove.security.properties.JwtProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@ConditionalOnExpression("${jwt.enable:true} and ${jwt.cors.enable:true}")
public class SecurityCorsConfiguration {

    private CorsProperties properties;

    public SecurityCorsConfiguration(CorsProperties corsProperties) {
        this.properties = corsProperties;
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin(properties.getOrigin());
        corsConfiguration.addAllowedHeader(properties.getHeader());
        corsConfiguration.addAllowedMethod(properties.getMethod());
        return corsConfiguration;
    }


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(properties.getPath(), buildConfig()); // 4
        return new CorsFilter(source);
    }
}

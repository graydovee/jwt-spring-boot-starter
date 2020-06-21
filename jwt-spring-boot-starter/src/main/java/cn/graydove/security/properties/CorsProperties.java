package cn.graydove.security.properties;

import lombok.Data;

@Data
public class CorsProperties {

    private boolean enable = true;

    private String origin = "*";

    private String header = "*";

    private String method = "*";

    private String path = "/**";
}

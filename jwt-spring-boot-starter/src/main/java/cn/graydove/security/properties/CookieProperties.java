package cn.graydove.security.properties;

import lombok.Data;

@Data
public class CookieProperties {

    private boolean enable = true;

    private boolean secure = false;

    private boolean httpOnly = false;
}

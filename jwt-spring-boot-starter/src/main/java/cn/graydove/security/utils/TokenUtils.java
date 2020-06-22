package cn.graydove.security.utils;

import cn.graydove.security.properties.TokenProperties;

public final class TokenUtils {

    private TokenUtils(){}

    public static String getTokenFromBearerToken(String bearerToken) {
        if (bearerToken == null) {
            return null;
        }
        String prefix = TokenProperties.TYPE_BEARER;
        if (!(bearerToken.length() > prefix.length()) || !bearerToken.startsWith(prefix)) {
            return null;
        }
        return bearerToken.substring(prefix.length() + 1);
    }

    public static String toBearerToken(String token) {
        return TokenProperties.TYPE_BEARER + " " + token;
    }

}

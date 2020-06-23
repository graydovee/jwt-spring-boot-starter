package cn.graydove.security.token;

public interface TokenManager {

    /**
     * 解密jwt
     */
    String parseToken(String jwt);

    String createToken(String subject);

    String createToken(String subject, long ttl);


}

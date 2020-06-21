package cn.graydove.security.token;

import cn.graydove.security.properties.TokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class TokenManager {

    private TokenProperties tokenProperties;

    public TokenManager(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
    }

    private SecretKey generalKey() {
        // 本地的密码解码
        byte[] encodedKey = Base64.getEncoder().encode(tokenProperties.getSecret().getBytes());
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }


    public String createJWT(String subject, long ttl) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();

        SecretKey key = generalKey();

        JwtBuilder builder = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(nowMillis))
                .setIssuer(tokenProperties.getIssuer())
                .setSubject(subject)
                .signWith(signatureAlgorithm, key);

        if (!tokenProperties.getClaims().isEmpty()) {
            builder.setClaims(tokenProperties.getClaims());
        }

        if (ttl >= 0) {
            long expMillis = nowMillis + tokenProperties.getTtl();
            builder.setExpiration(new Date(expMillis));
        }

        return builder.compact();
    }

    /**
     * 创建jwt
     */
    public String createJWT(String subject) {

        return createJWT(subject, tokenProperties.getTtl());
    }

    /**
     * 解密jwt
     */
    public Claims parseJWT(String jwt) {
        SecretKey key = generalKey();  //签名秘钥，和生成的签名的秘钥一模一样

        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
    }
}

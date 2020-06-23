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

public class JwtTokenManager implements TokenManager {
    private TokenProperties tokenProperties;

    private SecretKey key = null;

    public JwtTokenManager(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
    }

    private SecretKey getKey() {
        if (key == null) {
            byte[] encodedKey = Base64.getEncoder().encode(tokenProperties.getSecret().getBytes());
            key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        }
        return key;
    }


    @Override
    public String createToken(String subject, long ttl) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();

        SecretKey key = getKey();

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
    @Override
    public String createToken(String subject) {

        return createToken(subject, tokenProperties.getTtl());
    }

    /**
     * 解密jwt
     */
    @Override
    public String parseToken(String jwt) {
        SecretKey key = getKey();

        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwt).getBody();
        } catch (Exception e) {
            return null;
        }
        if (tokenProperties.getIssuer().equals(claims.getIssuer())) {
            return claims.getSubject();
        }
        return null;
    }
}

package com.rngay.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtUtil {

    private Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private String secret;
    private long expire;
    private String header;

    /**
     * 生成jwt token
     * */
    public String generateToken(Object userId){
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);

        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(String.valueOf(userId))
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Claims getClaimByToken(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e){
            logger.debug("validate is token error ", e);
            return null;
        }
    }

    /**
     * token是否过期
     * @return true: 过期
     * */
    public boolean isExpired(String token){
        Claims claimByToken = getClaimByToken(token);
        if (claimByToken == null){
            return true;
        }
        Date expiration = claimByToken.getExpiration();
        return expiration.before(new Date());
    }

    public String getSubject(String token){
        Claims claimByToken = getClaimByToken(token);
        if (claimByToken != null){
            return claimByToken.getSubject();
        }
        return null;
    }

    public Date getExpiration(String token){
        Claims claimByToken = getClaimByToken(token);
        if (claimByToken != null){
            return claimByToken.getExpiration();
        }
        return null;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

}

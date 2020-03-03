package com.rngay.common.util;

import com.rngay.common.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Autowired
    private JwtConfig jwtConfig;

    /**
     * 生成jwt token
     * */
    public String generateToken(Claims claims){
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + jwtConfig.getExpire() * 1000);

        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setClaims(claims)
                .setSubject(String.valueOf(claims.get("userId")))
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret())
                .compact();
    }

    public Claims getClaimByToken(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret())
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
        Claims claimByToken = getClaimByToken(getToken(token));
        if (claimByToken == null){
            return true;
        }
        Date expiration = claimByToken.getExpiration();
        return expiration.before(new Date());
    }

    public String getSubject(String token){
        Claims claimByToken = getClaimByToken(getToken(token));
        if (claimByToken != null){
            return claimByToken.getSubject();
        }
        return null;
    }

    public Date getExpiration(String token){
        Claims claimByToken = getClaimByToken(getToken(token));
        if (claimByToken != null){
            return claimByToken.getExpiration();
        }
        return null;
    }

    public String getToken(String token) {
        return token.replace("Bearer", "").trim();
    }

}

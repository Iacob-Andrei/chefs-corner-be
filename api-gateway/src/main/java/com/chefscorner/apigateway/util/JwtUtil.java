package com.chefscorner.apigateway.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.stereotype.Component;

import java.io.File;
import java.security.Key;

@Component
public class JwtUtil {

    static PropertiesConfiguration config;
    static {
        try {
            config = new PropertiesConfiguration(new File("D:\\Facultate\\Licenta\\chefs-corner-be\\.env"));
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
    public static final String SECRET = config.getString("secret.key");

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

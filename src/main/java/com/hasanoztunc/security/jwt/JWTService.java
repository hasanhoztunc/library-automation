package com.hasanoztunc.security.jwt;

import com.hasanoztunc.configurations.properties.ApplicationProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {

    // region Injections

    @Autowired
    private ApplicationProperties applicationProperties;

    // endregion

    // region Token Business

    public String generateToken(UserDetails userDetails) {
        return Jwts
                .builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserNameFromToken(String token) {
        return exportToken(token, Claims::getSubject);
    }

    public boolean checkIsTokenExpired(String token) {
        Date tokenExpireDate = exportToken(token, Claims::getExpiration);
        return new Date().after(tokenExpireDate);
    }

    // endregion

    // region Helpers

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(applicationProperties.getSecretKey());

        return Keys.hmacShaKeyFor(keyBytes);
    }

    private <T> T exportToken(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = getClaims(token);

        return claimsTFunction.apply(claims);
    }

    private Claims getClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // endregion
}

package com.DhrubaStudio.journalApp.config;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtilities {
    private final SecretKey signingKey;
    private final JwtParser jwtParser;

    JwtUtilities(@Value("${security.jwt.secret-key}") String secretKey) {
        this.signingKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.jwtParser = Jwts.parser().verifyWith(this.signingKey).build();
    }


    private Claims extractClaims(String token) {
        return jwtParser.parseSignedClaims(token).getPayload();
        /*return Jwts.parser().
                verifyWith(getSigningKey()).
                build().
                parseSignedClaims(token).
                getPayload();*/
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    private String createToken(Map<String,Object> claims,String subject){
        return Jwts.builder().
                claims(claims).
                subject(subject).
                header().empty().add("typ","JWT").and().
                issuedAt(new Date(System.currentTimeMillis())).
                expiration(new Date(System.currentTimeMillis() + 1000*60*5)).
                signWith(signingKey).
                compact();
    }

    public String generateToken(String username) {
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,username);
    }

    /*
    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
     */

}

package com.challenge.vendingmachine.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);


    private final String SECRET = "vending-machine";

    public String extractId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.MINUTE, (60 * 6));
        Date expiryTime = calendar.getTime();

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(now)
                .setExpiration(expiryTime)
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        LOGGER.info("username details {}", userDetails.getUsername());
        final String username = extractId(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

package sebanev15.taskmanager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sebanev15.taskmanager.model.User;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;
    public String generateToken(User user){
        SecretKey key = io.jsonwebtoken.security.Keys.hmacShaKeyFor(secretKey.getBytes());
        String jwt = Jwts.builder()
                .subject(user.getEmail())
                .signWith(key)
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .compact();
        return jwt;
    }

    public String extractEmail(String token){
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token, User user){
        String email = extractEmail(token);
        return (email.equals(user.getEmail()) && !extractAllClaims(token).getExpiration().before(new Date()));
    }

}

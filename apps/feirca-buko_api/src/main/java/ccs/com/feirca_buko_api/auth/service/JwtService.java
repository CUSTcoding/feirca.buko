package ccs.com.feirca_buko_api.auth.service;

import ccs.com.feirca_buko_api.auth.domain.entity.User;
import org.springframework.stereotype.Service;
import ccs.com.feirca_buko_api.config.JwtConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final JwtConfig  jwtConfig;

    public JwtService(JwtConfig jwtConfig){
        this.jwtConfig = jwtConfig;
    }

    public String generateToken(User user) {
        Key signingKey = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpirationMs()))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

}

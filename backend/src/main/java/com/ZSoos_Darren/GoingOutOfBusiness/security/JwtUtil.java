package com.ZSoos_Darren.GoingOutOfBusiness.security;

import com.ZSoos_Darren.GoingOutOfBusiness.model.GoobUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final String SECRET_KEY = SecretKeyGenerator.generateSecretKey(32); // Generate a 256-bit key

    public static String generateToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000); // Token expires in 1 hour

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static String generateTokenWithUser(GoobUser user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000); // Token expires in 1 hour

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());

        return Jwts.builder()
                .setSubject(user.getUserName())
                .addClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}

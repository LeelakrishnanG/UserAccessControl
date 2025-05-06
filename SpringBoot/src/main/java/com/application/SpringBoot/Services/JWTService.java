package com.application.SpringBoot.Services;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    private String SecretKeys;

    public JWTService(){
        try {
            KeyGenerator keyGen= KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk=keyGen.generateKey();
            SecretKeys = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            
            e.printStackTrace();
        }
    }

    public String generateToken(String username){

        Map <String, Object> claims = new HashMap<>();

        return Jwts.builder()
        .claims()
        .add(claims)
        .subject(username)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis()+ 60*60*60))
        .and()
        .signWith(getKey())
        .compact();

    }

    private SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SecretKeys);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        throw new UnsupportedOperationException("Unimplemented method 'extractUsername'");
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
        .verifyWith(getKey())
        .build().parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateToken'");
    }
}

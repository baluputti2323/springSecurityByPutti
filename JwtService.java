package com.example.spring.springSecurity.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {
    private String secretKey ="";

    public JwtService() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator =KeyGenerator.getInstance("HmacSHA256");
        SecretKey key = keyGenerator.generateKey();
        secretKey= Base64.getEncoder().encodeToString(key.getEncoded());
    }
    public String generateToken(String userName) {
        HashMap<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims().add(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*60*10))
                .and()
                .signWith(getKey())
                .compact();

    }

    private SecretKey getKey() {

        // we need the parameters in bytes, so we need to do some coversion
        byte[] decode = Base64.getDecoder().decode(secretKey);

        return Keys.hmacShaKeyFor(decode);
    }

    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
    }

    public boolean vaildateToken(String token, UserDetails userDetails) {
        String username = extractUserName(token);
        return username.equals(userDetails.getUsername()) && !tokenExpired(token);
    }

    private boolean tokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
//    public String generateToken(String username){
//
//    }
}

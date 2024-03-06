package com.ecomerce.my.ECommerce.project.security.jwt;


import com.ecomerce.my.ECommerce.project.constant.Constant;
import com.ecomerce.my.ECommerce.project.entity.Token;
import com.ecomerce.my.ECommerce.project.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTService {
    private final TokenRepository tokenRepository;
    // ToDo: extract claims
    public String extractUserEmail(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extraxtAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extraxtAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKet())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //    ToDo: validation
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserEmail(token);
        Token myToken = tokenRepository.findByToken(token).orElse(null);
        return myToken != null && userName.equals(userDetails.getUsername()) && !isTokenExpired(token) && !myToken.isExpired();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // todo: Generate claims
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignKet(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKet() {
        byte[] keyBytes = Decoders.BASE64.decode(Constant.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

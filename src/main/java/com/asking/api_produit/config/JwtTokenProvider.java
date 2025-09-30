package com.asking.api_produit.config;

import com.asking.api_produit.modele.security.Account;
import com.asking.api_produit.service.BaseFunction;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Getter
@Setter
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    private final String SECRET_KEY = "efb45d25baca1a97e5a462cdf6b9b8eb326f97560d9ed53805d5c8eefc3fc9b4";
//    private long EXPIRATION_TIME = 86400000; // 1 jour
    private long EXPIRATION_TIME = 600000; // 10 minute
    private long REFRESH_TOKEN_EXPIRATION_MS = 604800000; // 7 jours

    private final BaseFunction baseFunction;


    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        Account account = baseFunction.checkAccountByUsername(username);

        Map<String, Object> additionalClaims = Map.of(
                "isActive", true,
                "id", account.getId(),
                "phone", account.getPhone(),
                "username", account.getUsername(),
                "firstname", account.getFirstname(),
                "lastname", account.getLastname(),
                "authorities", account.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
        );

        return Jwts.builder()
                .subject(String.valueOf(account.getUsername()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claims(additionalClaims)
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(String username) {
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .subject(username)
                .id(UUID.randomUUID().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_MS))
                .signWith(secretKey)
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaimsFromToken(token);
        String username = claims.getSubject();
        List<GrantedAuthority> authorities = getAuthorities(claims);
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    private List<GrantedAuthority> getAuthorities(Claims claims) {
        List<String> roles = claims.get("authorities", List.class);
        if (roles == null) {
            return Collections.emptyList();
        }
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = getClaimsFromToken(token).getExpiration();
            return expiration.before(new Date());
        }catch (ExpiredJwtException e){
            return true;
        }
    }

    public String getTokenExpiresIn(String token) {
        return getClaimsFromToken(token).getExpiration().getTime() + "";
    }

    public String getTokenIatIn(String token) {
        return getClaimsFromToken(token).getIssuedAt().getTime() + "";
    }
}

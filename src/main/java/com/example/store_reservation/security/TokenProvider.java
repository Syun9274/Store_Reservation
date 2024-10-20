package com.example.store_reservation.security;

import com.example.store_reservation.model.enums.Authority;
import com.example.store_reservation.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TokenProvider {

    private static final String KEY_ROLES = "roles";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;
    private final UserService userService;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private Key key;

    @PostConstruct
    public void init() {
        // Bean 초기화 후에 Key 생성
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username, List<Authority> roles) {
        var claims = Jwts.claims().setSubject(username);

        List<String> roleNames = roles.stream()
                .map(Authority::name)
                .toList();
        claims.put(KEY_ROLES, roleNames);

        var now = new Date();
        var expirationDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userService.loadUserByUsername(getUsername(token));

        List<GrantedAuthority> authorities = getAuthorities(token);
        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    public String getUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            var claims = parseClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException e) {
            return e.getClaims();

        } catch (JwtException e) {
            throw new IllegalArgumentException("Invalid token");
        }
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        Claims claims = parseClaims(token);

        Object rolesObject = claims.get(KEY_ROLES);

        List<String> roles;
        if (rolesObject instanceof List) {
            roles = ((List<?>) rolesObject).stream()
                    .filter(item -> item instanceof String)
                    .map(item -> (String) item)
                    .toList();
        } else {
            roles = new ArrayList<>();
        }

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}

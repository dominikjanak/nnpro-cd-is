package cz.janakdom.backend.security;

import cz.janakdom.backend.model.database.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static cz.janakdom.backend.config.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static cz.janakdom.backend.config.Constants.SIGNING_KEY;

@Service(value = "jwtUtil")
public class JwtUtil implements Serializable {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public String extractToken(String bearer) {
        if (bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        } else {
            return bearer;
        }
    }

    public String extractUsername(String token) {
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
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("scopes", user.getRole().getName());
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();

        log.info("CREATED TOKEN: " + token);
        return token;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        Boolean expired = isTokenExpired(token);
        if (expired) {
            log.info("EXPIRED TOKEN: " + token);
        }
        return (username.equals(userDetails.getUsername()) && !expired);
    }
}

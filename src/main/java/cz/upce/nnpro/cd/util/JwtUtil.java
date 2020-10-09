package cz.upce.nnpro.cd.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class JwtUtil {

    @Value("jwt.secret")
    private String jwtSecret;

    private static final long EXPIRATION = 1440 * 60 * 1000; // 1 day in ms.

    public String generateToken(String userName) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserName(String token) {
        return getClaims(token).getSubject();
    }

    private Date getExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    public boolean validateToken(String token, UserDetails details) {
        String userName = getUserName(token);
        Date expiration = getExpiration(token);
        boolean isExpired = expiration.before(new Date());

        return !isExpired && details.getUsername().equals(userName);
    }

}

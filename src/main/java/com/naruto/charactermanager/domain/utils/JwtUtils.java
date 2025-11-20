package com.naruto.charactermanager.domain.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import com.naruto.charactermanager.domain.constants.AppConst;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Utilidades para manejo de JWT
 */
public class JwtUtils {
    
    private static Environment environment;
    
    public static void setEnvironment(Environment env) {
        environment = env;
    }
    
    /**
     * Genera un token JWT de acceso
     */
    public static String generateAccessToken(Long userId, String securityStamp) {
        return generateToken(userId, securityStamp, AppConst.ACCESS_TOKEN_TYPE, AppConst.ACCESS_TOKEN_TIME);
    }
    
    /**
     * Genera un token JWT de refresco
     */
    public static String generateRefreshToken(Long userId, String securityStamp) {
        return generateToken(userId, securityStamp, AppConst.REFRESH_TOKEN_TYPE, AppConst.REFRESH_TOKEN_TIME);
    }
    
    /**
     * Genera un token JWT
     */
    private static String generateToken(Long userId, String securityStamp, int tokenType, java.time.Duration expiration) {
        if (environment == null) {
            throw new IllegalStateException("Environment no ha sido configurado");
        }
        
        String issuer = environment.getProperty("jwt.issuer");
        String subject = environment.getProperty("jwt.subject");
        String securityKey = environment.getProperty(AppConst.JWT_SECURITY_KEY);
        
        if (securityKey == null) {
            throw new IllegalStateException("jwt.securityKey no está configurado");
        }
        
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityKey));
        
        Map<String, Object> claims = new HashMap<>();
        claims.put(AppConst.USER_ID, userId.toString());
        claims.put(AppConst.SECURITY_STAMP, securityStamp);
        claims.put(AppConst.TOKEN_TYPE, String.valueOf(tokenType));
        
        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(subject)
                .setClaims(claims)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(expiration)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    
    /**
     * Valida y extrae los claims de un token JWT
     */
    public static Optional<Claims> validateAndExtractClaims(String token) {
        try {
            if (environment == null) {
                return Optional.empty();
            }
            
            String securityKey = environment.getProperty(AppConst.JWT_SECURITY_KEY);
            if (securityKey == null) {
                return Optional.empty();
            }
            
            Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityKey));
            
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            return Optional.of(claims);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    /**
     * Extrae el userId de un token
     */
    public static Optional<Long> extractUserId(String token) {
        return validateAndExtractClaims(token)
                .map(claims -> Long.valueOf(claims.get(AppConst.USER_ID, String.class)));
    }
    
    /**
     * Extrae el security stamp de un token
     */
    public static Optional<String> extractSecurityStamp(String token) {
        return validateAndExtractClaims(token)
                .map(claims -> claims.get(AppConst.SECURITY_STAMP, String.class));
    }
    
    /**
     * Extrae el tipo de token
     */
    public static Optional<Integer> extractTokenType(String token) {
        return validateAndExtractClaims(token)
                .map(claims -> Integer.valueOf(claims.get(AppConst.TOKEN_TYPE, String.class)));
    }
    
    /**
     * Verifica si un token es válido
     */
    public static boolean isTokenValid(String token) {
        return validateAndExtractClaims(token).isPresent();
    }
    
    /**
     * Verifica si un token ha expirado
     */
    public static boolean isTokenExpired(String token) {
        return validateAndExtractClaims(token)
                .map(claims -> claims.getExpiration().before(new Date()))
                .orElse(true);
    }
}


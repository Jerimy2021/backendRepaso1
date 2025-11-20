package com.naruto.charactermanager.domain.constants;

import java.time.Duration;

/**
 * Constantes de la aplicación para JWT y seguridad
 */
public class AppConst {
    
    // JWT Claims
    public static final String USER_ID = "userId";
    public static final String SECURITY_STAMP = "securityStamp";
    public static final String TOKEN_TYPE = "tokenType";
    public static final String JWT_SECURITY_KEY = "jwt.securityKey";
    
    // Token Types
    public static final int ACCESS_TOKEN_TYPE = 0;
    public static final int REFRESH_TOKEN_TYPE = 1;
    
    // Token Expiration Times
    public static final Duration ACCESS_TOKEN_TIME = Duration.ofHours(1); // 1 hora
    public static final Duration REFRESH_TOKEN_TIME = Duration.ofDays(1); // 1 día
    
    // Security Constants
    public static final int MAX_LOGIN_ATTEMPTS = 3;
    public static final Duration LOCKOUT_DURATION = Duration.ofMinutes(5);
    
    // Password Constants
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 128;
    
    // User Constants
    public static final int MAX_USERNAME_LENGTH = 50;
    public static final int MAX_EMAIL_LENGTH = 100;
    public static final int MAX_DOCUMENT_LENGTH = 20;
    public static final int MAX_NAME_LENGTH = 100;
    
    // Error Messages
    public static final String INVALID_CREDENTIALS = "DNI o contraseña inválidos";
    public static final String USER_LOCKED = "Usuario bloqueado por intentos fallidos";
    public static final String INVALID_TOKEN = "Token inválido o expirado";
    public static final String UNAUTHORIZED_ACCESS = "Acceso no autorizado";
}


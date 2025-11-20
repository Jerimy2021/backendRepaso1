package com.naruto.charactermanager.application.dto;

import java.time.Duration;

/**
 * DTO para respuestas de login
 */
public class LoginResponse {
    
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpirationSeconds;
    private Long refreshTokenExpirationSeconds;
    private String encryptedAccessToken;
    private Boolean hasExpiredPassword;
    private UserInfo userInfo;
    
    // Constructor
    public LoginResponse() {}
    
    public LoginResponse(String accessToken, String refreshToken, 
                       Duration accessTokenExpiration, Duration refreshTokenExpiration,
                       String encryptedAccessToken, Boolean hasExpiredPassword, UserInfo userInfo) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpirationSeconds = accessTokenExpiration.getSeconds();
        this.refreshTokenExpirationSeconds = refreshTokenExpiration.getSeconds();
        this.encryptedAccessToken = encryptedAccessToken;
        this.hasExpiredPassword = hasExpiredPassword;
        this.userInfo = userInfo;
    }
    
    // Getters y Setters
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getRefreshToken() {
        return refreshToken;
    }
    
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    
    public Long getAccessTokenExpirationSeconds() {
        return accessTokenExpirationSeconds;
    }
    
    public void setAccessTokenExpirationSeconds(Long accessTokenExpirationSeconds) {
        this.accessTokenExpirationSeconds = accessTokenExpirationSeconds;
    }
    
    public Long getRefreshTokenExpirationSeconds() {
        return refreshTokenExpirationSeconds;
    }
    
    public void setRefreshTokenExpirationSeconds(Long refreshTokenExpirationSeconds) {
        this.refreshTokenExpirationSeconds = refreshTokenExpirationSeconds;
    }
    
    public String getEncryptedAccessToken() {
        return encryptedAccessToken;
    }
    
    public void setEncryptedAccessToken(String encryptedAccessToken) {
        this.encryptedAccessToken = encryptedAccessToken;
    }
    
    public Boolean getHasExpiredPassword() {
        return hasExpiredPassword;
    }
    
    public void setHasExpiredPassword(Boolean hasExpiredPassword) {
        this.hasExpiredPassword = hasExpiredPassword;
    }
    
    public UserInfo getUserInfo() {
        return userInfo;
    }
    
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    
    /**
     * DTO interno para información del usuario
     */
    public static class UserInfo {
        private Long id;
        private String documento;
        private String nombreUsuario;
        private String nombre;
        private String email;
        
        // Constructor
        public UserInfo() {}
        
        public UserInfo(Long id, String documento, String nombreUsuario, String nombre, String email) {
            this.id = id;
            this.documento = documento;
            this.nombreUsuario = nombreUsuario;
            this.nombre = nombre;
            this.email = email;
        }
        
        // Getters y Setters
        public Long getId() {
            return id;
        }
        
        public void setId(Long id) {
            this.id = id;
        }
        
        public String getDocumento() {
            return documento;
        }
        
        public void setDocumento(String documento) {
            this.documento = documento;
        }
        
        public String getNombreUsuario() {
            return nombreUsuario;
        }
        
        public void setNombreUsuario(String nombreUsuario) {
            this.nombreUsuario = nombreUsuario;
        }
        
        public String getNombre() {
            return nombre;
        }
        
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
    }
}


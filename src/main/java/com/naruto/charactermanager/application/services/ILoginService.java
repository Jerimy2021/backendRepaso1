package com.naruto.charactermanager.application.services;

import com.naruto.charactermanager.application.dto.LoginResponse;

/**
 * Servicio para manejo de autenticación
 */
public interface ILoginService {
    
    /**
     * Autentica un usuario por DNI y contraseña
     */
    LoginResponse authenticateByDocumento(String documento, String contrasena);
    
    /**
     * Refresca un token de acceso usando un refresh token
     */
    LoginResponse refreshToken(String refreshToken);
    
    /**
     * Cierra la sesión de un usuario
     */
    void logout(String documento);
}


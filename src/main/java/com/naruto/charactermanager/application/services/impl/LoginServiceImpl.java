package com.naruto.charactermanager.application.services.impl;

import com.naruto.charactermanager.application.dto.LoginResponse;
import com.naruto.charactermanager.application.exceptions.AuthenticationException;
import com.naruto.charactermanager.application.services.ILoginService;
import com.naruto.charactermanager.application.services.IUsuarioService;
import com.naruto.charactermanager.domain.constants.AppConst;
import com.naruto.charactermanager.domain.utils.JwtUtils;
import com.naruto.charactermanager.infrastructure.entities.UsuarioEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * Implementación del servicio de login
 */
@Service
public class LoginServiceImpl implements ILoginService {
    
    private final IUsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    
    public LoginServiceImpl(IUsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public LoginResponse authenticateByDocumento(String documento, String contrasena) {
        Optional<UsuarioEntity> usuarioOptional = usuarioService.findByDocumento(documento);
        
        if (usuarioOptional.isEmpty()) {
            throw new AuthenticationException(AppConst.INVALID_CREDENTIALS, "INVALID_CREDENTIALS");
        }
        
        UsuarioEntity usuario = usuarioOptional.get();
        
        if (!usuario.getActive()) {
            throw new AuthenticationException("Usuario inactivo", "USER_INACTIVE");
        }
        
        if (usuario.isLocked()) {
            throw new AuthenticationException(AppConst.USER_LOCKED, "USER_LOCKED");
        }
        
        if (!usuarioService.passwordMatches(usuario, contrasena)) {
            usuario.incrementFailedAttempts();
            
            if (usuario.getAccessFailedCount() >= AppConst.MAX_LOGIN_ATTEMPTS) {
                usuario.lockAccount(new Timestamp(System.currentTimeMillis() + AppConst.LOCKOUT_DURATION.toMillis()));
            }
            
            usuarioService.updateUsuario(usuario, "SYSTEM");
            throw new AuthenticationException(AppConst.INVALID_CREDENTIALS, "INVALID_CREDENTIALS");
        }
        
        if (usuario.getAccessFailedCount() > 0) {
            usuario.resetFailedAttempts();
            usuarioService.updateUsuario(usuario, "SYSTEM");
        }
        
        String accessToken = JwtUtils.generateAccessToken(usuario.getId(), usuario.getSecurityStamp());
        String refreshToken = JwtUtils.generateRefreshToken(usuario.getId(), usuario.getSecurityStamp());
        
        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
            usuario.getId(),
            usuario.getDocumento(),
            usuario.getNombreUsuario(),
            usuario.getNombre(),
            usuario.getEmail()
        );
        
        return new LoginResponse(
            accessToken,
            refreshToken,
            AppConst.ACCESS_TOKEN_TIME,
            AppConst.REFRESH_TOKEN_TIME,
            null,
            false,
            userInfo
        );
    }
    
    @Override
    public LoginResponse refreshToken(String refreshToken) {
        if (!JwtUtils.isTokenValid(refreshToken)) {
            throw new AuthenticationException(AppConst.INVALID_TOKEN, "INVALID_TOKEN");
        }
        
        Optional<Long> userIdOptional = JwtUtils.extractUserId(refreshToken);
        Optional<String> securityStampOptional = JwtUtils.extractSecurityStamp(refreshToken);
        Optional<Integer> tokenTypeOptional = JwtUtils.extractTokenType(refreshToken);
        
        if (userIdOptional.isEmpty() || securityStampOptional.isEmpty() || tokenTypeOptional.isEmpty()) {
            throw new AuthenticationException(AppConst.INVALID_TOKEN, "INVALID_TOKEN");
        }
        
        if (tokenTypeOptional.get() != AppConst.REFRESH_TOKEN_TYPE) {
            throw new AuthenticationException(AppConst.INVALID_TOKEN, "INVALID_TOKEN");
        }
        
        Long userId = userIdOptional.get();
        String securityStamp = securityStampOptional.get();
        
        Optional<UsuarioEntity> usuarioOptional = usuarioService.findById(userId);
        if (usuarioOptional.isEmpty()) {
            throw new AuthenticationException(AppConst.INVALID_TOKEN, "INVALID_TOKEN");
        }
        
        UsuarioEntity usuario = usuarioOptional.get();
        
        if (!usuario.getActive() || !usuario.getSecurityStamp().equals(securityStamp)) {
            throw new AuthenticationException(AppConst.INVALID_TOKEN, "INVALID_TOKEN");
        }
        
        String newAccessToken = JwtUtils.generateAccessToken(usuario.getId(), usuario.getSecurityStamp());
        String newRefreshToken = JwtUtils.generateRefreshToken(usuario.getId(), usuario.getSecurityStamp());
        
        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
            usuario.getId(),
            usuario.getDocumento(),
            usuario.getNombreUsuario(),
            usuario.getNombre(),
            usuario.getEmail()
        );
        
        return new LoginResponse(
            newAccessToken,
            newRefreshToken,
            AppConst.ACCESS_TOKEN_TIME,
            AppConst.REFRESH_TOKEN_TIME,
            null,
            false,
            userInfo
        );
    }
    
    @Override
    public void logout(String documento) {
        Optional<UsuarioEntity> usuarioOptional = usuarioService.findByDocumento(documento);
        if (usuarioOptional.isPresent()) {
            UsuarioEntity usuario = usuarioOptional.get();
            usuario.updateSecurityStamp();
            usuarioService.updateUsuario(usuario, "SYSTEM");
        }
    }
}


package com.naruto.charactermanager.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.naruto.charactermanager.application.dto.LoginRequest;
import com.naruto.charactermanager.application.dto.LoginResponse;
import com.naruto.charactermanager.application.services.ILoginService;
import com.naruto.charactermanager.presentation.shared.web.WebResponse;

/**
 * Controlador para autenticación
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints para autenticación y autorización")
@CrossOrigin(origins = "*")
public class AuthController {
    
    private final ILoginService loginService;
    
    public AuthController(ILoginService loginService) {
        this.loginService = loginService;
    }
    
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario por DNI y contraseña")
    public WebResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = loginService.authenticateByDocumento(request.getDocumento(), request.getContrasena());
        return WebResponse.of(response);
    }
    
    @PostMapping("/refresh")
    @Operation(summary = "Refrescar token", description = "Refresca el token de acceso usando un refresh token")
    public WebResponse<LoginResponse> refreshToken(@RequestParam String refreshToken) {
        LoginResponse response = loginService.refreshToken(refreshToken);
        return WebResponse.of(response);
    }
    
    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión", description = "Cierra la sesión del usuario")
    public WebResponse<String> logout(@RequestParam String documento) {
        loginService.logout(documento);
        return WebResponse.of("Sesión cerrada exitosamente");
    }
}


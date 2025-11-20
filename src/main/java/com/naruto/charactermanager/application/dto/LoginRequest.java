package com.naruto.charactermanager.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * DTO para solicitudes de login
 */
public class LoginRequest {
    
    @Schema(description = "Documento de identidad (DNI)", example = "12345678", required = true)
    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^[0-9]+$", message = "El DNI solo debe contener números")
    private String documento;
    
    @Schema(description = "Contraseña del usuario", example = "Admin123!", required = true)
    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;
    
    // Constructores
    public LoginRequest() {}
    
    public LoginRequest(String documento, String contrasena) {
        this.documento = documento;
        this.contrasena = contrasena;
    }
    
    // Getters y Setters
    public String getDocumento() {
        return documento;
    }
    
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    
    public String getContrasena() {
        return contrasena;
    }
    
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}


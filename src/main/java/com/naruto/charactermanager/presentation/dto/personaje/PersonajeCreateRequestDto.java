package com.naruto.charactermanager.presentation.dto.personaje;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de request para crear personajes
 */
public class PersonajeCreateRequestDto {
    
    @NotBlank(message = "El nombre del personaje es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @Size(max = 50, message = "La aldea no puede exceder 50 caracteres")
    private String aldea;
    
    @Size(max = 50, message = "El rango no puede exceder 50 caracteres")
    private String rango;
    
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String descripcion;
    
    @Size(max = 500, message = "Las técnicas no pueden exceder 500 caracteres")
    private String tecnicas;
    
    // Constructor por defecto
    public PersonajeCreateRequestDto() {}
    
    // Constructor con todos los campos
    public PersonajeCreateRequestDto(String nombre, String aldea, String rango, String descripcion, String tecnicas) {
        this.nombre = nombre;
        this.aldea = aldea;
        this.rango = rango;
        this.descripcion = descripcion;
        this.tecnicas = tecnicas;
    }
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getAldea() {
        return aldea;
    }
    
    public void setAldea(String aldea) {
        this.aldea = aldea;
    }
    
    public String getRango() {
        return rango;
    }
    
    public void setRango(String rango) {
        this.rango = rango;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getTecnicas() {
        return tecnicas;
    }
    
    public void setTecnicas(String tecnicas) {
        this.tecnicas = tecnicas;
    }
}


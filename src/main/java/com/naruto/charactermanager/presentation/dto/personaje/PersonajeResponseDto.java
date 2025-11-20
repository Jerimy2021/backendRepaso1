package com.naruto.charactermanager.presentation.dto.personaje;

import java.time.LocalDateTime;

/**
 * DTO de response para personajes
 */
public class PersonajeResponseDto {
    
    private Long id;
    private String nombre;
    private String aldea;
    private String rango;
    private String descripcion;
    private String tecnicas;
    private LocalDateTime createdDate;
    private String createdBy;
    
    // Constructor por defecto
    public PersonajeResponseDto() {}
    
    // Constructor con todos los campos
    public PersonajeResponseDto(Long id, String nombre, String aldea, String rango, 
                               String descripcion, String tecnicas, LocalDateTime createdDate, String createdBy) {
        this.id = id;
        this.nombre = nombre;
        this.aldea = aldea;
        this.rango = rango;
        this.descripcion = descripcion;
        this.tecnicas = tecnicas;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}


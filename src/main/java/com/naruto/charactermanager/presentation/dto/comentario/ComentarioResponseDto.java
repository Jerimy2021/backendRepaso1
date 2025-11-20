package com.naruto.charactermanager.presentation.dto.comentario;

import java.time.LocalDateTime;

/**
 * DTO de response para comentarios
 */
public class ComentarioResponseDto {
    
    private Long id;
    private String contenido;
    private String autor;
    private Integer calificacion;
    private Long personajeId;
    private String personajeNombre;
    private LocalDateTime createdDate;
    private String createdBy;
    
    // Constructor por defecto
    public ComentarioResponseDto() {}
    
    // Constructor con todos los campos
    public ComentarioResponseDto(Long id, String contenido, String autor, Integer calificacion,
                                Long personajeId, String personajeNombre, LocalDateTime createdDate, String createdBy) {
        this.id = id;
        this.contenido = contenido;
        this.autor = autor;
        this.calificacion = calificacion;
        this.personajeId = personajeId;
        this.personajeNombre = personajeNombre;
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
    
    public String getContenido() {
        return contenido;
    }
    
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    public String getAutor() {
        return autor;
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public Integer getCalificacion() {
        return calificacion;
    }
    
    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }
    
    public Long getPersonajeId() {
        return personajeId;
    }
    
    public void setPersonajeId(Long personajeId) {
        this.personajeId = personajeId;
    }
    
    public String getPersonajeNombre() {
        return personajeNombre;
    }
    
    public void setPersonajeNombre(String personajeNombre) {
        this.personajeNombre = personajeNombre;
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


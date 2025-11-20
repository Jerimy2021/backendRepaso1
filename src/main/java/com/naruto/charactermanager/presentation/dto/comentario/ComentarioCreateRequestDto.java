package com.naruto.charactermanager.presentation.dto.comentario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO de request para crear comentarios
 */
public class ComentarioCreateRequestDto {
    
    @NotBlank(message = "El contenido del comentario es obligatorio")
    @Size(max = 1000, message = "El comentario no puede exceder 1000 caracteres")
    private String contenido;
    
    @NotBlank(message = "El autor del comentario es obligatorio")
    @Size(max = 100, message = "El nombre del autor no puede exceder 100 caracteres")
    private String autor;
    
    private Integer calificacion;
    
    @NotNull(message = "El ID del personaje es obligatorio")
    private Long personajeId;
    
    // Constructor por defecto
    public ComentarioCreateRequestDto() {}
    
    // Constructor con todos los campos
    public ComentarioCreateRequestDto(String contenido, String autor, Integer calificacion, Long personajeId) {
        this.contenido = contenido;
        this.autor = autor;
        this.calificacion = calificacion;
        this.personajeId = personajeId;
    }
    
    // Getters y Setters
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
}


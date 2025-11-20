package com.naruto.charactermanager.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Comment;
import com.naruto.charactermanager.domain.inheritance.FullAuditedEntity;

import java.io.Serializable;

/**
 * Entidad JPA para persistencia de comentarios sobre personajes
 */
@Entity
@Comment("Comentario sobre un personaje")
@Table(name = "COMENTARIO")
public class ComentarioEntity extends FullAuditedEntity implements Serializable {

    @Id
    @Comment("Identificador único del comentario")
    @Column(name = "ID_COMENTARIO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("Contenido del comentario")
    @Column(name = "CONTENIDO", length = 1000, nullable = false)
    @NotBlank(message = "El contenido del comentario es obligatorio")
    @Size(max = 1000, message = "El comentario no puede exceder 1000 caracteres")
    private String contenido;

    @Comment("Autor del comentario")
    @Column(name = "AUTOR", length = 100, nullable = false)
    @NotBlank(message = "El autor del comentario es obligatorio")
    @Size(max = 100, message = "El nombre del autor no puede exceder 100 caracteres")
    private String autor;

    @Comment("Calificación del personaje (1-5)")
    @Column(name = "CALIFICACION")
    private Integer calificacion;

    // Relación con Personaje
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PERSONAJE", nullable = false)
    @NotNull(message = "El personaje es obligatorio")
    private PersonajeEntity personaje;

    // Constructores
    public ComentarioEntity() {
    }

    public ComentarioEntity(String contenido, String autor, Integer calificacion, PersonajeEntity personaje) {
        this.contenido = contenido;
        this.autor = autor;
        this.calificacion = calificacion;
        this.personaje = personaje;
    }

    // Métodos de auditoría simplificados
    public void createComentario(String userId) {
        create(userId);
    }

    public void updateComentario(String userId) {
        modify(userId);
    }

    public void deleteComentario(String userId) {
        delete(userId);
    }

    public void restoreComentario(String userId) {
        unDelete();
        modify(userId);
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

    public PersonajeEntity getPersonaje() {
        return personaje;
    }

    public void setPersonaje(PersonajeEntity personaje) {
        this.personaje = personaje;
    }
}


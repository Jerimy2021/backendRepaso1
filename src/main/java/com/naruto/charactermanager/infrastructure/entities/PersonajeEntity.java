package com.naruto.charactermanager.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Comment;
import com.naruto.charactermanager.domain.inheritance.FullAuditedEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Entidad JPA para persistencia de personajes de Naruto
 */
@Entity
@Comment("Personaje del universo Naruto")
@Table(name = "PERSONAJE")
public class PersonajeEntity extends FullAuditedEntity implements Serializable {

    @Id
    @Comment("Identificador único del personaje")
    @Column(name = "ID_PERSONAJE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("Nombre del personaje")
    @Column(name = "NOMBRE", length = 100, nullable = false)
    @NotBlank(message = "El nombre del personaje es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @Comment("Aldea de origen del personaje")
    @Column(name = "ALDEA", length = 50)
    @Size(max = 50, message = "La aldea no puede exceder 50 caracteres")
    private String aldea;

    @Comment("Rango del personaje (Genin, Chūnin, Jōnin, Kage, etc.)")
    @Column(name = "RANGO", length = 50)
    @Size(max = 50, message = "El rango no puede exceder 50 caracteres")
    private String rango;

    @Comment("Descripción del personaje")
    @Column(name = "DESCRIPCION", length = 1000)
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String descripcion;

    @Comment("Técnicas especiales del personaje")
    @Column(name = "TECNICAS", length = 500)
    @Size(max = 500, message = "Las técnicas no pueden exceder 500 caracteres")
    private String tecnicas;

    // Relaciones
    @JsonManagedReference
    @OneToMany(mappedBy = "personaje", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ComentarioEntity> comentarios;

    // Constructores
    public PersonajeEntity() {
    }

    public PersonajeEntity(String nombre, String aldea, String rango, String descripcion, String tecnicas) {
        this.nombre = nombre;
        this.aldea = aldea;
        this.rango = rango;
        this.descripcion = descripcion;
        this.tecnicas = tecnicas;
    }

    // Métodos de auditoría simplificados
    public void createPersonaje(String userId) {
        create(userId);
    }

    public void updatePersonaje(String userId) {
        modify(userId);
    }

    public void deletePersonaje(String userId) {
        delete(userId);
    }

    public void restorePersonaje(String userId) {
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

    public List<ComentarioEntity> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioEntity> comentarios) {
        this.comentarios = comentarios;
    }
}


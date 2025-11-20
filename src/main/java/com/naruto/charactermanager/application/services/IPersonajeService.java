package com.naruto.charactermanager.application.services;

import com.naruto.charactermanager.infrastructure.entities.PersonajeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para manejo de personajes
 */
public interface IPersonajeService {
    
    /**
     * Busca un personaje por ID
     */
    Optional<PersonajeEntity> findById(Long id);
    
    /**
     * Obtiene todos los personajes
     */
    List<PersonajeEntity> findAll();
    
    /**
     * Lista paginada de personajes con filtros dinámicos
     */
    Page<PersonajeEntity> findAll(Specification<PersonajeEntity> specification, Pageable pageable);
    
    /**
     * Crea un nuevo personaje
     */
    PersonajeEntity createPersonaje(PersonajeEntity personaje, String creatorId);
    
    /**
     * Actualiza un personaje existente
     */
    PersonajeEntity updatePersonaje(PersonajeEntity personaje, String updaterId);
    
    /**
     * Elimina un personaje (soft delete)
     */
    void deletePersonaje(Long personajeId, String deleterId);
}


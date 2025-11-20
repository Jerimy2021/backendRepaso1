package com.naruto.charactermanager.application.services;

import com.naruto.charactermanager.infrastructure.entities.ComentarioEntity;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para manejo de comentarios
 */
public interface IComentarioService {
    
    /**
     * Busca un comentario por ID
     */
    Optional<ComentarioEntity> findById(Long id);
    
    /**
     * Obtiene todos los comentarios de un personaje
     */
    List<ComentarioEntity> findByPersonajeId(Long personajeId);
    
    /**
     * Crea un nuevo comentario
     */
    ComentarioEntity createComentario(ComentarioEntity comentario, String creatorId);
    
    /**
     * Actualiza un comentario existente
     */
    ComentarioEntity updateComentario(ComentarioEntity comentario, String updaterId);
    
    /**
     * Elimina un comentario (soft delete)
     */
    void deleteComentario(Long comentarioId, String deleterId);
}


package com.naruto.charactermanager.application.services.impl;

import com.naruto.charactermanager.application.services.IComentarioService;
import com.naruto.charactermanager.application.services.IPersonajeService;
import com.naruto.charactermanager.infrastructure.entities.ComentarioEntity;
import com.naruto.charactermanager.infrastructure.entities.PersonajeEntity;
import com.naruto.charactermanager.infrastructure.repositories.IComentarioRepository;
import com.naruto.charactermanager.presentation.shared.web.WebException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de comentarios
 */
@Service
@Transactional
public class ComentarioServiceImpl implements IComentarioService {
    
    private final IComentarioRepository comentarioRepository;
    private final IPersonajeService personajeService;
    
    public ComentarioServiceImpl(IComentarioRepository comentarioRepository, IPersonajeService personajeService) {
        this.comentarioRepository = comentarioRepository;
        this.personajeService = personajeService;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<ComentarioEntity> findById(Long id) {
        return comentarioRepository.findByIdAndNotDeleted(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ComentarioEntity> findByPersonajeId(Long personajeId) {
        return comentarioRepository.findByPersonajeIdAndNotDeleted(personajeId);
    }
    
    @Override
    public ComentarioEntity createComentario(ComentarioEntity comentario, String creatorId) {
        // Verificar que el personaje existe
        PersonajeEntity personaje = personajeService.findById(comentario.getPersonaje().getId())
                .orElseThrow(() -> new WebException("Personaje no encontrado"));
        
        comentario.setPersonaje(personaje);
        comentario.createComentario(creatorId);
        return comentarioRepository.save(comentario);
    }
    
    @Override
    public ComentarioEntity updateComentario(ComentarioEntity comentario, String updaterId) {
        ComentarioEntity existingComentario = comentarioRepository.findByIdAndNotDeleted(comentario.getId())
                .orElseThrow(() -> new WebException("Comentario no encontrado"));
        
        existingComentario.setContenido(comentario.getContenido());
        existingComentario.setAutor(comentario.getAutor());
        existingComentario.setCalificacion(comentario.getCalificacion());
        
        existingComentario.updateComentario(updaterId);
        return comentarioRepository.save(existingComentario);
    }
    
    @Override
    public void deleteComentario(Long comentarioId, String deleterId) {
        ComentarioEntity comentario = comentarioRepository.findByIdAndNotDeleted(comentarioId)
                .orElseThrow(() -> new WebException("Comentario no encontrado"));
        comentario.deleteComentario(deleterId);
        comentarioRepository.save(comentario);
    }
}


package com.naruto.charactermanager.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.naruto.charactermanager.application.services.IComentarioService;
import com.naruto.charactermanager.application.services.IPersonajeService;
import com.naruto.charactermanager.infrastructure.entities.ComentarioEntity;
import com.naruto.charactermanager.infrastructure.entities.PersonajeEntity;
import com.naruto.charactermanager.presentation.dto.comentario.ComentarioCreateRequestDto;
import com.naruto.charactermanager.presentation.dto.comentario.ComentarioResponseDto;
import com.naruto.charactermanager.presentation.shared.web.WebException;
import com.naruto.charactermanager.presentation.shared.web.WebResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para gestión de comentarios
 */
@RestController
@RequestMapping("/api/comentario")
@Tag(name = "Comentarios", description = "Endpoints para gestión de comentarios sobre personajes")
@CrossOrigin(origins = "*")
public class ComentarioController {
    
    private final IComentarioService comentarioService;
    private final IPersonajeService personajeService;
    
    public ComentarioController(IComentarioService comentarioService, IPersonajeService personajeService) {
        this.comentarioService = comentarioService;
        this.personajeService = personajeService;
    }
    
    @PostMapping
    @Operation(summary = "Agregar comentario", description = "Agrega un nuevo comentario a un personaje")
    public WebResponse<ComentarioResponseDto> createComentario(@Valid @RequestBody ComentarioCreateRequestDto request) {
        try {
            String creatorId = "SYSTEM";
            
            // Obtener el personaje
            PersonajeEntity personaje = personajeService.findById(request.getPersonajeId())
                    .orElseThrow(() -> new WebException("Personaje no encontrado"));
            
            ComentarioEntity comentario = new ComentarioEntity();
            comentario.setContenido(request.getContenido());
            comentario.setAutor(request.getAutor());
            comentario.setCalificacion(request.getCalificacion());
            comentario.setPersonaje(personaje);
            
            ComentarioEntity createdComentario = comentarioService.createComentario(comentario, creatorId);
            
            ComentarioResponseDto responseDto = mapComentarioEntityToResponseDto(createdComentario);
            return WebResponse.of(responseDto);
            
        } catch (Exception e) {
            throw new WebException("Error al crear comentario: " + e.getMessage());
        }
    }
    
    @GetMapping("/{personaje_id}")
    @Operation(summary = "Ver comentarios", description = "Obtiene todos los comentarios de un personaje específico")
    public WebResponse<List<ComentarioResponseDto>> getComentariosByPersonajeId(@PathVariable("personaje_id") Long personajeId) {
        try {
            List<ComentarioEntity> comentarios = comentarioService.findByPersonajeId(personajeId);
            
            List<ComentarioResponseDto> responseDtos = comentarios.stream()
                    .map(this::mapComentarioEntityToResponseDto)
                    .collect(Collectors.toList());
            
            return WebResponse.of(responseDtos);
            
        } catch (Exception e) {
            throw new WebException("Error al obtener comentarios: " + e.getMessage());
        }
    }

    /**
     * Mapea manualmente una entidad ComentarioEntity a ComentarioResponseDto
     */
    private ComentarioResponseDto mapComentarioEntityToResponseDto(ComentarioEntity comentario) {
        ComentarioResponseDto dto = new ComentarioResponseDto();
        
        dto.setId(comentario.getId());
        dto.setContenido(comentario.getContenido());
        dto.setAutor(comentario.getAutor());
        dto.setCalificacion(comentario.getCalificacion());
        
        if (comentario.getPersonaje() != null) {
            dto.setPersonajeId(comentario.getPersonaje().getId());
            dto.setPersonajeNombre(comentario.getPersonaje().getNombre());
        }
        
        if (comentario.getCreationTime() != null) {
            dto.setCreatedDate(comentario.getCreationTime().toLocalDateTime());
        }
        dto.setCreatedBy(comentario.getCreatorUserId());
        
        return dto;
    }
}


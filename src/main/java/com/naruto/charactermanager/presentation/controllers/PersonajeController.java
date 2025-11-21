package com.naruto.charactermanager.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import com.naruto.charactermanager.application.services.IPersonajeService;
import com.naruto.charactermanager.infrastructure.entities.PersonajeEntity;
import com.naruto.charactermanager.infrastructure.utils.SearchBuilder;
import com.naruto.charactermanager.presentation.dto.common.PagedResultDto;
import com.naruto.charactermanager.presentation.dto.personaje.PersonajeCreateRequestDto;
import com.naruto.charactermanager.presentation.dto.personaje.PersonajeResponseDto;
import com.naruto.charactermanager.presentation.shared.web.WebException;
import com.naruto.charactermanager.presentation.shared.web.WebResponse;

import java.util.stream.Collectors;

/**
 * Controlador para gestión de personajes
 */
@RestController
@RequestMapping("/api/personajes")
@Tag(name = "Personajes", description = "Endpoints para gestión de personajes de Naruto")
@SecurityRequirement(name = "bearer-jwt")
@CrossOrigin(origins = "*")
public class PersonajeController {
    
    private final IPersonajeService personajeService;
    private final ModelMapper mapper;
    
    public PersonajeController(IPersonajeService personajeService, ModelMapper mapper) {
        this.personajeService = personajeService;
        this.mapper = mapper;
    }
    
    @GetMapping
    @Operation(summary = "Listar personajes", description = "Lista paginada de personajes con filtros opcionales")
    public WebResponse<PagedResultDto<PersonajeResponseDto>> getAll(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String aldea,
            @RequestParam(defaultValue = "creationTime DESC") String sorting,
            @RequestParam(defaultValue = "10") Integer maxResultCount,
            @RequestParam(defaultValue = "0") Integer skipCount
    ) {
        String[] sortParts = sorting.split(" ");
        String sortFieldRaw = sortParts[0];
        Sort.Direction sortDirection = sortParts.length > 1 && "DESC".equalsIgnoreCase(sortParts[1])
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        String sortField;
        if ("createdDate".equalsIgnoreCase(sortFieldRaw)) {
            sortField = "creationTime";
        } else if ("modifiedDate".equalsIgnoreCase(sortFieldRaw) || "updatedDate".equalsIgnoreCase(sortFieldRaw)) {
            sortField = "lastModificationTime";
        } else {
            sortField = sortFieldRaw;
        }

        int size = (maxResultCount == null || maxResultCount <= 0) ? 10 : maxResultCount;
        int offset = (skipCount == null || skipCount < 0) ? 0 : skipCount;
        var pageable = PageRequest.of(offset / Math.max(1, size), size, Sort.by(sortDirection, sortField));

        Specification<PersonajeEntity> specification = (root, query, builder) -> {
            var search = new SearchBuilder<PersonajeEntity>(root, query, builder)
                    .like("nombre", nombre)
                    .like("aldea", aldea)
                    .notDeleted();
            return search.getPredicate();
        };

        var page = personajeService.findAll(specification, pageable);
        var content = page.getContent().stream()
                .map(this::mapPersonajeEntityToResponseDto)
                .collect(Collectors.toList());
        return WebResponse.of(PagedResultDto.of(page.getTotalElements(), content));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener personaje por ID", description = "Obtiene un personaje específico por su ID")
    public WebResponse<PersonajeResponseDto> getPersonajeById(@PathVariable Long id) {
        try {
            return personajeService.findById(id)
                    .map(personaje -> {
                        PersonajeResponseDto personajeDto = mapPersonajeEntityToResponseDto(personaje);
                        return WebResponse.of(personajeDto);
                    })
                    .orElseThrow(() -> new WebException("Personaje no encontrado"));
                    
        } catch (Exception e) {
            throw new WebException("Error al obtener personaje: " + e.getMessage());
        }
    }
    
    @PostMapping
    @Operation(summary = "Crear personaje", description = "Crea un nuevo personaje")
    public WebResponse<PersonajeResponseDto> createPersonaje(@Valid @RequestBody PersonajeCreateRequestDto request) {
        try {
            String creatorId = "SYSTEM";
            
            PersonajeEntity personaje = new PersonajeEntity();
            personaje.setNombre(request.getNombre());
            personaje.setAldea(request.getAldea());
            personaje.setRango(request.getRango());
            personaje.setDescripcion(request.getDescripcion());
            personaje.setTecnicas(request.getTecnicas());
            
            PersonajeEntity createdPersonaje = personajeService.createPersonaje(personaje, creatorId);
            
            PersonajeResponseDto responseDto = mapPersonajeEntityToResponseDto(createdPersonaje);
            return WebResponse.of(responseDto);
            
        } catch (Exception e) {
            throw new WebException("Error al crear personaje: " + e.getMessage());
        }
    }

    /**
     * Mapea manualmente una entidad PersonajeEntity a PersonajeResponseDto
     */
    private PersonajeResponseDto mapPersonajeEntityToResponseDto(PersonajeEntity personaje) {
        PersonajeResponseDto dto = new PersonajeResponseDto();
        
        dto.setId(personaje.getId());
        dto.setNombre(personaje.getNombre());
        dto.setAldea(personaje.getAldea());
        dto.setRango(personaje.getRango());
        dto.setDescripcion(personaje.getDescripcion());
        dto.setTecnicas(personaje.getTecnicas());
        
        if (personaje.getCreationTime() != null) {
            dto.setCreatedDate(personaje.getCreationTime().toLocalDateTime());
        }
        dto.setCreatedBy(personaje.getCreatorUserId());
        
        return dto;
    }
}


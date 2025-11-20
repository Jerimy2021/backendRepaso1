package com.naruto.charactermanager.application.services.impl;

import com.naruto.charactermanager.application.services.IPersonajeService;
import com.naruto.charactermanager.infrastructure.entities.PersonajeEntity;
import com.naruto.charactermanager.infrastructure.repositories.IPersonajeRepository;
import com.naruto.charactermanager.presentation.shared.web.WebException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de personajes
 */
@Service
@Transactional
public class PersonajeServiceImpl implements IPersonajeService {
    
    private final IPersonajeRepository personajeRepository;
    
    public PersonajeServiceImpl(IPersonajeRepository personajeRepository) {
        this.personajeRepository = personajeRepository;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<PersonajeEntity> findById(Long id) {
        return personajeRepository.findByIdAndNotDeleted(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PersonajeEntity> findAll() {
        return personajeRepository.findAllNotDeleted();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PersonajeEntity> findAll(Specification<PersonajeEntity> specification, Pageable pageable) {
        return personajeRepository.findAll(specification, pageable);
    }
    
    @Override
    public PersonajeEntity createPersonaje(PersonajeEntity personaje, String creatorId) {
        personaje.createPersonaje(creatorId);
        return personajeRepository.save(personaje);
    }
    
    @Override
    public PersonajeEntity updatePersonaje(PersonajeEntity personaje, String updaterId) {
        PersonajeEntity existingPersonaje = personajeRepository.findByIdAndNotDeleted(personaje.getId())
                .orElseThrow(() -> new WebException("Personaje no encontrado"));
        
        existingPersonaje.setNombre(personaje.getNombre());
        existingPersonaje.setAldea(personaje.getAldea());
        existingPersonaje.setRango(personaje.getRango());
        existingPersonaje.setDescripcion(personaje.getDescripcion());
        existingPersonaje.setTecnicas(personaje.getTecnicas());
        
        existingPersonaje.updatePersonaje(updaterId);
        return personajeRepository.save(existingPersonaje);
    }
    
    @Override
    public void deletePersonaje(Long personajeId, String deleterId) {
        PersonajeEntity personaje = personajeRepository.findByIdAndNotDeleted(personajeId)
                .orElseThrow(() -> new WebException("Personaje no encontrado"));
        personaje.deletePersonaje(deleterId);
        personajeRepository.save(personaje);
    }
}


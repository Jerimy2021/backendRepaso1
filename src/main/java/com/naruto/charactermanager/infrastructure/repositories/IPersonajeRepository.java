package com.naruto.charactermanager.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.naruto.charactermanager.infrastructure.entities.PersonajeEntity;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Personaje
 */
@Repository
public interface IPersonajeRepository extends JpaRepository<PersonajeEntity, Long>, JpaSpecificationExecutor<PersonajeEntity> {

    /**
     * Busca un personaje por ID que no esté eliminado
     */
    @Query("SELECT p FROM PersonajeEntity p WHERE p.id = :id AND p.isDeleted = false")
    Optional<PersonajeEntity> findByIdAndNotDeleted(@Param("id") Long id);

    /**
     * Busca todos los personajes que no estén eliminados
     */
    @Query("SELECT p FROM PersonajeEntity p WHERE p.isDeleted = false")
    List<PersonajeEntity> findAllNotDeleted();

    /**
     * Busca personajes por nombre que no estén eliminados
     */
    @Query("SELECT p FROM PersonajeEntity p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND p.isDeleted = false")
    List<PersonajeEntity> findByNombreContainingIgnoreCaseAndNotDeleted(@Param("nombre") String nombre);

    /**
     * Busca personajes por aldea que no estén eliminados
     */
    @Query("SELECT p FROM PersonajeEntity p WHERE LOWER(p.aldea) LIKE LOWER(CONCAT('%', :aldea, '%')) AND p.isDeleted = false")
    List<PersonajeEntity> findByAldeaContainingIgnoreCaseAndNotDeleted(@Param("aldea") String aldea);
}


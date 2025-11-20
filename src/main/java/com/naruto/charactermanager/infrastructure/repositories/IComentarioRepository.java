package com.naruto.charactermanager.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.naruto.charactermanager.infrastructure.entities.ComentarioEntity;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Comentario
 */
@Repository
public interface IComentarioRepository extends JpaRepository<ComentarioEntity, Long>, JpaSpecificationExecutor<ComentarioEntity> {

    /**
     * Busca un comentario por ID que no esté eliminado
     */
    @Query("SELECT c FROM ComentarioEntity c WHERE c.id = :id AND c.isDeleted = false")
    Optional<ComentarioEntity> findByIdAndNotDeleted(@Param("id") Long id);

    /**
     * Busca todos los comentarios de un personaje que no estén eliminados
     */
    @Query("SELECT c FROM ComentarioEntity c WHERE c.personaje.id = :personajeId AND c.isDeleted = false ORDER BY c.creationTime DESC")
    List<ComentarioEntity> findByPersonajeIdAndNotDeleted(@Param("personajeId") Long personajeId);

    /**
     * Busca todos los comentarios que no estén eliminados
     */
    @Query("SELECT c FROM ComentarioEntity c WHERE c.isDeleted = false ORDER BY c.creationTime DESC")
    List<ComentarioEntity> findAllNotDeleted();
}


package com.naruto.charactermanager.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.naruto.charactermanager.infrastructure.entities.UsuarioEntity;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Usuario
 */
@Repository
public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, Long>, JpaSpecificationExecutor<UsuarioEntity> {

    /**
     * Busca un usuario por DNI que no esté eliminado
     */
    @Query("SELECT u FROM UsuarioEntity u WHERE u.documento = :documento AND u.isDeleted = false")
    Optional<UsuarioEntity> findByDocumentoAndNotDeleted(@Param("documento") String documento);

    /**
     * Busca un usuario por nombre de usuario que no esté eliminado
     */
    @Query("SELECT u FROM UsuarioEntity u WHERE u.nombreUsuario = :nombreUsuario AND u.isDeleted = false")
    Optional<UsuarioEntity> findByNombreUsuarioAndNotDeleted(@Param("nombreUsuario") String nombreUsuario);

    /**
     * Busca un usuario por email que no esté eliminado
     */
    @Query("SELECT u FROM UsuarioEntity u WHERE u.email = :email AND u.isDeleted = false")
    Optional<UsuarioEntity> findByEmailAndNotDeleted(@Param("email") String email);

    /**
     * Busca un usuario por ID que no esté eliminado
     */
    @Query("SELECT u FROM UsuarioEntity u WHERE u.id = :id AND u.isDeleted = false")
    Optional<UsuarioEntity> findByIdAndNotDeleted(@Param("id") Long id);

    /**
     * Busca todos los usuarios activos que no estén eliminados
     */
    @Query("SELECT u FROM UsuarioEntity u WHERE u.isActive = true AND u.isDeleted = false")
    List<UsuarioEntity> findAllActiveAndNotDeleted();

    /**
     * Verifica si existe un usuario con el DNI especificado
     */
    @Query("SELECT COUNT(u) > 0 FROM UsuarioEntity u WHERE u.documento = :documento AND u.isDeleted = false")
    boolean existsByDocumentoAndNotDeleted(@Param("documento") String documento);
}


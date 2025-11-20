package com.naruto.charactermanager.application.services;

import com.naruto.charactermanager.infrastructure.entities.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para manejo de usuarios
 */
public interface IUsuarioService {
    
    /**
     * Busca un usuario por ID
     */
    Optional<UsuarioEntity> findById(Long id);
    
    /**
     * Busca un usuario por DNI
     */
    Optional<UsuarioEntity> findByDocumento(String documento);
    
    /**
     * Busca un usuario por nombre de usuario
     */
    Optional<UsuarioEntity> findByNombreUsuario(String nombreUsuario);
    
    /**
     * Busca un usuario por email
     */
    Optional<UsuarioEntity> findByEmail(String email);
    
    /**
     * Obtiene todos los usuarios activos
     */
    List<UsuarioEntity> findAllActive();

    /**
     * Lista paginada de usuarios con filtros dinámicos
     */
    Page<UsuarioEntity> findAll(Specification<UsuarioEntity> specification, Pageable pageable);
    
    /**
     * Crea un nuevo usuario
     */
    UsuarioEntity createUsuario(UsuarioEntity usuario, String creatorId);
    
    /**
     * Actualiza un usuario existente
     */
    UsuarioEntity updateUsuario(UsuarioEntity usuario, String updaterId);
    
    /**
     * Elimina un usuario (soft delete)
     */
    void deleteUsuario(Long usuarioId, String deleterId);
    
    /**
     * Verifica si una contraseña coincide con la del usuario
     */
    boolean passwordMatches(UsuarioEntity usuario, String password);
    
    /**
     * Encripta una contraseña
     */
    String encryptPassword(String password);
    
    /**
     * Actualiza la contraseña de un usuario
     */
    void updatePassword(UsuarioEntity usuario, String newPassword, String updaterId);
}


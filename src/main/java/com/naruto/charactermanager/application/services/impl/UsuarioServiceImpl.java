package com.naruto.charactermanager.application.services.impl;

import com.naruto.charactermanager.application.services.IUsuarioService;
import com.naruto.charactermanager.infrastructure.entities.UsuarioEntity;
import com.naruto.charactermanager.infrastructure.repositories.IUsuarioRepository;
import com.naruto.charactermanager.presentation.shared.web.WebException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de usuarios
 */
@Service
@Transactional
public class UsuarioServiceImpl implements IUsuarioService {
    
    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UsuarioServiceImpl(IUsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioEntity> findById(Long id) {
        return usuarioRepository.findByIdAndNotDeleted(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioEntity> findByDocumento(String documento) {
        return usuarioRepository.findByDocumentoAndNotDeleted(documento);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioEntity> findByNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuarioAndNotDeleted(nombreUsuario);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioEntity> findByEmail(String email) {
        return usuarioRepository.findByEmailAndNotDeleted(email);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UsuarioEntity> findAllActive() {
        return usuarioRepository.findAllActiveAndNotDeleted();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioEntity> findAll(Specification<UsuarioEntity> specification, Pageable pageable) {
        return usuarioRepository.findAll(specification, pageable);
    }
    
    @Override
    public UsuarioEntity createUsuario(UsuarioEntity usuario, String creatorId) {
        Optional<UsuarioEntity> existingUsuarioByDocumento = usuarioRepository.findByDocumentoAndNotDeleted(usuario.getDocumento());
        if (existingUsuarioByDocumento.isPresent()) {
            throw new WebException("Ya existe un usuario con el DNI: " + usuario.getDocumento());
        }
        Optional<UsuarioEntity> existingUsuarioByNombreUsuario = usuarioRepository.findByNombreUsuarioAndNotDeleted(usuario.getNombreUsuario());
        if (existingUsuarioByNombreUsuario.isPresent()) {
            throw new WebException("Ya existe un usuario con el nombre de usuario: " + usuario.getNombreUsuario());
        }
        if (usuario.getEmail() != null && !usuario.getEmail().trim().isEmpty()) {
            Optional<UsuarioEntity> existingUsuarioByEmail = usuarioRepository.findByEmailAndNotDeleted(usuario.getEmail());
            if (existingUsuarioByEmail.isPresent()) {
                throw new WebException("Ya existe un usuario con el email: " + usuario.getEmail());
            }
        }
        usuario.normalize();
        usuario.setContrasena(encryptPassword(usuario.getContrasena()));
        usuario.createUsuario(creatorId);
        return usuarioRepository.save(usuario);
    }
    
    @Override
    public UsuarioEntity updateUsuario(UsuarioEntity usuario, String updaterId) {
        usuario.normalize();
        usuario.updateUsuario(updaterId);
        return usuarioRepository.save(usuario);
    }
    
    @Override
    public void deleteUsuario(Long usuarioId, String deleterId) {
        Optional<UsuarioEntity> usuarioOptional = usuarioRepository.findByIdAndNotDeleted(usuarioId);
        if (usuarioOptional.isPresent()) {
            UsuarioEntity usuario = usuarioOptional.get();
            usuario.deleteUsuario(deleterId);
            usuarioRepository.save(usuario);
        }
    }
    
    @Override
    public boolean passwordMatches(UsuarioEntity usuario, String password) {
        return passwordEncoder.matches(password, usuario.getContrasena());
    }
    
    @Override
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
    
    @Override
    public void updatePassword(UsuarioEntity usuario, String newPassword, String updaterId) {
        usuario.setContrasena(encryptPassword(newPassword));
        usuario.updateUsuario(updaterId);
        usuarioRepository.save(usuario);
    }
}


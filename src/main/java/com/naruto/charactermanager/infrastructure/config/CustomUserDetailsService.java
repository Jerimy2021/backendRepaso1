package com.naruto.charactermanager.infrastructure.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.naruto.charactermanager.application.services.IUsuarioService;
import com.naruto.charactermanager.infrastructure.entities.UsuarioEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio personalizado de UserDetails para Spring Security
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    private final IUsuarioService usuarioService;
    
    public CustomUserDetailsService(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar usuario por DNI
        Optional<UsuarioEntity> usuarioOptional = usuarioService.findByDocumento(username);
        
        if (usuarioOptional.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
        
        UsuarioEntity usuario = usuarioOptional.get();
        
        // Verificar que el usuario esté activo
        if (!usuario.getActive()) {
            throw new UsernameNotFoundException("Usuario inactivo: " + username);
        }
        
        // Crear autoridades (roles básicos)
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        return User.builder()
            .username(usuario.getDocumento())
            .password(usuario.getContrasena())
            .authorities(authorities)
            .accountExpired(false)
            .accountLocked(usuario.isLocked())
            .credentialsExpired(false)
            .disabled(!usuario.getActive())
            .build();
    }
}


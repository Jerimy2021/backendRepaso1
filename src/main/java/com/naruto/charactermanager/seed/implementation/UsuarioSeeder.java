package com.naruto.charactermanager.seed.implementation;

import com.naruto.charactermanager.application.services.IUsuarioService;
import com.naruto.charactermanager.infrastructure.entities.UsuarioEntity;
import com.naruto.charactermanager.seed.declaration.IUsuarioSeeder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implementación del seeder de usuarios
 */
@Service
public class UsuarioSeeder implements IUsuarioSeeder {

    private final IUsuarioService usuarioService;

    public UsuarioSeeder(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    @Transactional
    public void seed() {
        createAdminUser();
        createTestUser();
    }

    private void createAdminUser() {
        Optional<UsuarioEntity> existingAdmin = usuarioService.findByDocumento("12345678");
        if (existingAdmin.isEmpty()) {
            UsuarioEntity adminUser = new UsuarioEntity(
                "12345678", 
                "admin", 
                "Administrador del Sistema", 
                "admin@naruto.com", 
                "Admin123!"
            );
            adminUser.setEmailConfirmed(true);
            adminUser.setActive(true);
            usuarioService.createUsuario(adminUser, "SYSTEM");
        }
    }

    private void createTestUser() {
        Optional<UsuarioEntity> existingUser = usuarioService.findByDocumento("87654321");
        if (existingUser.isEmpty()) {
            UsuarioEntity testUser = new UsuarioEntity(
                "87654321", 
                "usuario", 
                "Usuario de Prueba", 
                "usuario@naruto.com", 
                "User123!"
            );
            testUser.setEmailConfirmed(true);
            testUser.setActive(true);
            usuarioService.createUsuario(testUser, "SYSTEM");
        }
    }
}


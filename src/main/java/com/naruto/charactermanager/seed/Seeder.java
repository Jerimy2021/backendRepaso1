package com.naruto.charactermanager.seed;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import com.naruto.charactermanager.seed.declaration.IUsuarioSeeder;

/**
 * Seeder principal que ejecuta todos los seeders
 */
@Service
public class Seeder {

    private final IUsuarioSeeder usuarioSeeder;

    public Seeder(IUsuarioSeeder usuarioSeeder) {
        this.usuarioSeeder = usuarioSeeder;
    }

    @Bean
    public CommandLineRunner seed() {
        return ignored -> {
            usuarioSeeder.seed();
        };
    }
}


-- Script de inicialización para PostgreSQL
-- Este script se ejecuta automáticamente la primera vez que se crea la base de datos

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

SELECT 'CREATE DATABASE sonarqube'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'sonarqube')\gexec

GRANT ALL PRIVILEGES ON DATABASE naruto_character_manager TO naruto_user;
GRANT ALL PRIVILEGES ON DATABASE sonarqube TO naruto_user;

ALTER SYSTEM SET max_connections = 200;
ALTER SYSTEM SET shared_buffers = '256MB';
ALTER SYSTEM SET effective_cache_size = '1GB';


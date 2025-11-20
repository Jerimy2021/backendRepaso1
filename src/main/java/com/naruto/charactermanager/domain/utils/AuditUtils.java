package com.naruto.charactermanager.domain.utils;

import java.sql.Timestamp;

/**
 * Utilidades para auditoría simplificada
 */
public class AuditUtils {
    
    /**
     * Obtiene el timestamp actual
     * @return Timestamp actual
     */
    public static Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }
    
    /**
     * Valida si un identificador de usuario es válido
     * @param userId Identificador del usuario
     * @return true si es válido, false en caso contrario
     */
    public static boolean isValidUserId(String userId) {
        return userId != null && !userId.trim().isEmpty() && userId.length() <= 50;
    }
    
    /**
     * Obtiene un identificador válido o null si no es válido
     * @param userId Identificador del usuario
     * @return Identificador válido o null
     */
    public static String getValidUserId(String userId) {
        return isValidUserId(userId) ? userId.trim() : "SYSTEM";
    }
}


package com.naruto.charactermanager.presentation.dto.common;

import java.util.List;

/**
 * DTO para resultados paginados
 * Contiene la información de paginación y los datos
 */
public class PagedResultDto<T> {
    
    private long totalElements;
    private List<T> content;
    
    // Constructor por defecto
    public PagedResultDto() {}
    
    // Constructor con todos los campos
    public PagedResultDto(long totalElements, List<T> content) {
        this.totalElements = totalElements;
        this.content = content;
    }
    
    // Método estático para crear instancias
    public static <T> PagedResultDto<T> of(long totalElements, List<T> content) {
        return new PagedResultDto<>(totalElements, content);
    }
    
    // Getters y Setters
    public long getTotalElements() {
        return totalElements;
    }
    
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
    
    public List<T> getContent() {
        return content;
    }
    
    public void setContent(List<T> content) {
        this.content = content;
    }
}


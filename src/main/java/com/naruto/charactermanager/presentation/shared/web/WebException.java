package com.naruto.charactermanager.presentation.shared.web;

public class WebException extends RuntimeException {

    private static final String DEFAULT_TITLE = "Aviso";

    private final String title;
    private final String message;
    private final int status;

    public WebException(String title, String message, int status) {
        this.title = title;
        this.message = message;
        this.status = status;
    }
    
    public WebException(String title, String message) {
        this.title = title;
        this.message = message;
        this.status = 0;
    }

    public WebException(String message) {
        this.title = DEFAULT_TITLE;
        this.message = message;
        this.status = 0;
    }

    public WebException(String message, int status) {
        this.title = DEFAULT_TITLE;
        this.message = message;
        this.status = status;
    }
    
    public String getTitle() {
        return title;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
    
    public int getStatus() {
        return status;
    }
}


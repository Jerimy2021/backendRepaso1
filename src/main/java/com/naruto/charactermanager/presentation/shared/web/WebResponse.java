package com.naruto.charactermanager.presentation.shared.web;

public class WebResponse<T> {
    private final T result;
    private final Boolean success;
    private final Boolean app;

    public WebResponse(T result) {
        this.result = result;
        this.success = Boolean.TRUE;
        this.app = Boolean.TRUE;
    }

    public static <T> WebResponse<T> of(T result) {
        return new WebResponse<>(result);
    }
    
    public T getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public Boolean getApp() {
        return app;
    }
}


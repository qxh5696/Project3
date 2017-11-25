package com.rit.edu.cs.copads.p3.exceptions;

public class BoardConstructException extends Exception {

    private String message;

    public BoardConstructException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

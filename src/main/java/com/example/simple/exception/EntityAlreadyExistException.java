package com.example.simple.exception;

public class EntityAlreadyExistException extends RuntimeException {

    public EntityAlreadyExistException(String massage) {
        super(massage);
    }
}

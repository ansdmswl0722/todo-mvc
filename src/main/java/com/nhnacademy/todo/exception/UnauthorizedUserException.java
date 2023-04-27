package com.nhnacademy.todo.exception;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 16/03/2023
 */
public class UnauthorizedUserException extends RuntimeException {
    private static final String MESSAGE = "Unauthorized";
    public UnauthorizedUserException(){
        super(MESSAGE);
    }
}

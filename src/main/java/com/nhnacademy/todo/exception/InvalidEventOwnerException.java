package com.nhnacademy.todo.exception;

/**
 * @Author : marco@nhnedu.com
 * @Date : 24/11/2022
 */
public class InvalidEventOwnerException extends RuntimeException {
    private static final String MESSAGE="잘못된 이벤트 소유자 ";
    public InvalidEventOwnerException(){
        super(MESSAGE);
    }

}

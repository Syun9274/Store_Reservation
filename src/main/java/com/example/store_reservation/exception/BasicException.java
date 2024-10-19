package com.example.store_reservation.exception;

import lombok.Getter;

@Getter
public abstract class BasicException extends RuntimeException {

    abstract public int statusCode();
    abstract public String errorMessage();

}

package com.bigbig.exception;

public abstract class ServiceException extends Exception {
    ServiceException(String expMessage) {
        super(expMessage);
    }

}

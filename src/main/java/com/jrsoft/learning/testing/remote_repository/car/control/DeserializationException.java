package com.jrsoft.learning.testing.remote_repository.car.control;

import javax.ws.rs.ProcessingException;

public class DeserializationException extends RuntimeException {
    public DeserializationException(ProcessingException e) {
        super(e);
    }
}

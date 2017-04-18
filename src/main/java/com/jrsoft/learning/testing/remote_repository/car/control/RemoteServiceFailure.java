package com.jrsoft.learning.testing.remote_repository.car.control;

import javax.ws.rs.core.Response;

public class RemoteServiceFailure extends RuntimeException {
    public RemoteServiceFailure(String message, Response response) {
        super(String.format("%s (%d): %s",
                message,
                response.getStatusInfo().getStatusCode(),
                response.getStatusInfo().getReasonPhrase())
        );
    }
}

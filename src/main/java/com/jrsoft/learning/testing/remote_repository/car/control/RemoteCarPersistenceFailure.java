package com.jrsoft.learning.testing.remote_repository.car.control;

import com.jrsoft.learning.testing.remote_repository.car.entity.Car;

import javax.ws.rs.core.Response;

public class RemoteCarPersistenceFailure extends RemoteServiceFailure {
    public RemoteCarPersistenceFailure(Car car, Response response) {
        super("Could not save Car", response);
    }
}

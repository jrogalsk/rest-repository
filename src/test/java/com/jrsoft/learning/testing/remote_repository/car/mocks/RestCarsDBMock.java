package com.jrsoft.learning.testing.remote_repository.car.mocks;

import com.jrsoft.learning.testing.remote_repository.car.entity.RestCar;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class RestCarsDBMock {
    private Map<String, RestCar> cars = new HashMap<>();

    public Map<String, RestCar> getCars() {
        return cars;
    }
}

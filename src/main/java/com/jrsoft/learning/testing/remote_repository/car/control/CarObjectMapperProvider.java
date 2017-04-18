package com.jrsoft.learning.testing.remote_repository.car.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.jrsoft.learning.testing.remote_repository.car.entity.Engine;

import javax.ws.rs.ext.ContextResolver;

public class CarObjectMapperProvider implements ContextResolver<ObjectMapper> {
    private final ObjectMapper objectMapper;

    public CarObjectMapperProvider() {
        objectMapper = createMapper();
    }

    private ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerSubtypes(
                new NamedType(Engine.class, Engine.class.getSimpleName())
        );
        return mapper;
    }


    @Override
    public ObjectMapper getContext(Class<?> type) {
        return objectMapper;
    }
}

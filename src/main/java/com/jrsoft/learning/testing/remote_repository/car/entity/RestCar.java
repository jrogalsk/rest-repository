package com.jrsoft.learning.testing.remote_repository.car.entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jrsoft.learning.testing.remote_repository.commons.Identifiable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RestCar implements Identifiable {
    private String id;
    private String name;

    private Map<String, Object> undefinedAttributes = new HashMap<>();

    @Override
    public String getId() {
        return this.id;
    }


    @JsonDeserialize(as = String.class)
    @Override
    public void setId(Serializable id) {
        this.id = (String) id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonAnyGetter
    public Map<String, Object> undefinedAttributes() {
        return undefinedAttributes;
    }

    @JsonAnySetter
    public void setUndefinedAttribute(String attributeName, Object attributeValue) {
        undefinedAttributes.put(attributeName, attributeValue);
    }

}

package com.jrsoft.learning.testing.remote_repository.commons;

import java.io.Serializable;

public interface Repository<T> {
    T create(T entity);

    <PK extends Serializable> T read(PK key);

    T update(T entity);

    T refresh(T entity);
}

package com.jrsoft.learning.testing.remote_repository.commons;

import java.io.Serializable;

public interface Identifiable {
    Serializable getId();
    void setId(Serializable id);
}

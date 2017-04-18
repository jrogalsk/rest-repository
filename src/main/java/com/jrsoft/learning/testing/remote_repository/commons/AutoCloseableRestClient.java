package com.jrsoft.learning.testing.remote_repository.commons;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Map;

public class AutoCloseableRestClient implements Client, AutoCloseable {
    private final Client client;

    public AutoCloseableRestClient(Client client) {
        this.client = client;
    }

    @Override
    public void close() {
        client.close();
    }

    @Override
    public WebTarget target(String uri) {
        return client.target(uri);
    }

    @Override
    public WebTarget target(URI uri) {
      return client.target(uri);
    }

    @Override
    public WebTarget target(UriBuilder uriBuilder) {
        return client.target(uriBuilder);
    }

    @Override
    public WebTarget target(Link link) {
        return client.target(link);
    }

    @Override
    public Invocation.Builder invocation(Link link) {
        return client.invocation(link);
    }

    @Override
    public SSLContext getSslContext() {
        return client.getSslContext();
    }

    @Override
    public HostnameVerifier getHostnameVerifier() {
       return client.getHostnameVerifier();
    }

    @Override
    public Configuration getConfiguration() {
        return client.getConfiguration();
    }

    @Override
    public Client property(String name, Object value) {
        return client.property(name, value);
    }

    @Override
    public Client register(Class<?> componentClass) {
        return client.register(componentClass);
    }

    @Override
    public Client register(Class<?> componentClass, int priority) {
        return client.register(componentClass, priority);
    }

    @Override
    public Client register(Class<?> componentClass, Class<?>... contracts) {
       return client.register(componentClass, contracts);
    }

    @Override
    public Client register(Class<?> componentClass, Map<Class<?>, Integer> contracts) {
        return client.register(componentClass, contracts);
    }

    @Override
    public Client register(Object component) {
        return client.register(component);
    }

    @Override
    public Client register(Object component, int priority) {
        return client.register(component, priority);
    }

    @Override
    public Client register(Object component, Class<?>... contracts) {
        return client.register(component, contracts);
    }

    @Override
    public Client register(Object component, Map<Class<?>, Integer> contracts) {
        return client.register(component, contracts);
    }

}

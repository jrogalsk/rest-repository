package com.jrsoft.learning.testing.remote_repository.car.control;


import com.jrsoft.learning.testing.remote_repository.car.mocks.RestCarsCountExternalResourceMock;
import com.jrsoft.learning.testing.remote_repository.car.mocks.RestCarsDBMock;
import com.jrsoft.learning.testing.remote_repository.car.mocks.RestCarsExternalResourceMock;
import com.jrsoft.learning.testing.remote_repository.car.mocks.RestCarsFacetExternalResourceMock;
import com.jrsoft.learning.testing.remote_repository.car.entity.Car;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.test.TestPortProvider;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class RestCarsRepositoryTest {
    private static UndertowJaxrsServer server;
    private RestCarsRepository restCarsRepository;

    @BeforeClass
    public static void init() {
        server = new UndertowJaxrsServer().start();
    }

    @AfterClass
    public static void stop() {
        server.stop();
    }

    @Before
    public void setUp() {
        deployResources();
        initRepository();
    }

    private void deployResources() {
        server.deploy(RestConfig.class);
    }

    private void initRepository() {
        this.restCarsRepository = new TestableRestCarRepository();
        this.restCarsRepository.remoteCarsServiceUrl = TestPortProvider.generateBaseUrl() + "/cars";
    }

    @Test
    public void whenCarHasNoId_assignRandomId() {
        Car car = new Car();
        car.setName("CarWithNoId");

        Car created = restCarsRepository.create(car);

        assertThat(created.getName(), is(car.getName()));
        assertNotNull(created.getId());
    }

    @ApplicationPath("cars")
    public static class RestConfig extends Application {
        @Override
        public Set<Object> getSingletons() {
            Set<Object> classes = new HashSet<>();
            RestCarsDBMock restCarsDBMock = new RestCarsDBMock();
            classes.add(new RestCarsExternalResourceMock(restCarsDBMock));
            classes.add(new RestCarsCountExternalResourceMock(restCarsDBMock));
            classes.add(new RestCarsFacetExternalResourceMock(restCarsDBMock));
            classes.add(restCarsDBMock);
            return classes;
        }

    }

}

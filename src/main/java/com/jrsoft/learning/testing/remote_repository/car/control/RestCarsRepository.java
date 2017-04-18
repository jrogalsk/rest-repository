package com.jrsoft.learning.testing.remote_repository.car.control;

import com.jrsoft.learning.testing.remote_repository.car.entity.Car;
import com.jrsoft.learning.testing.remote_repository.commons.AutoCloseableRestClient;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.net.URI;
import java.util.function.BiFunction;
import java.util.logging.Logger;


/**
 * Connects to remote, REST based microservice
 */
@Default
public class RestCarsRepository implements CarsRepository {
    @Inject
    String remoteCarsServiceUrl;

    @Inject
    Logger logger;

    private BiFunction<Client, Car, Response> createEndpoint = (client, car) ->
            client.target(remoteCarsServiceUrl).path("resources/cars")
                    .request().post(Entity.json(car));

    @Override
    public Car create(Car car) {
        try (AutoCloseableRestClient client = getRestClient()) {
            Response response = createEndpoint.apply(client, car);

            if(response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
                throw new RemoteCarPersistenceFailure(car, response);
            }
            else {
                return read(response.getLocation());
            }
        }
    }

    private Car read(URI uri) {
        try (AutoCloseableRestClient client = getRestClient()) {
            Response response = client.target(uri).request().get();
            return deserializeCar(response);
        }
    }

    private Car deserializeCar(Response response) {
        try {
            return response.readEntity(Car.class);
        }
        catch (ProcessingException e) {
            throw new DeserializationException(e);
        }
    }

    private AutoCloseableRestClient getRestClient() {
        Client client = ClientBuilder.newClient()
                .register(CarObjectMapperProvider.class);

        return new AutoCloseableRestClient(client);
    }

    @Override
    public <PK extends Serializable> Car read(PK key) {
        throw new UnsupportedOperationException("#read()");
    }

    @Override
    public Car update(Car entity) {
        throw new UnsupportedOperationException("#update()");
    }

    @Override
    public Car refresh(Car entity) {
        throw new UnsupportedOperationException("#refresh()");
    }
}

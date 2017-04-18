package com.jrsoft.learning.testing.remote_repository.car.mocks;

import com.jrsoft.learning.testing.remote_repository.car.entity.RestCar;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Path("resources/cars")
public class RestCarsExternalResourceMock {
    private final RestCarsDBMock restCarsDBMock;


    public RestCarsExternalResourceMock(RestCarsDBMock restCarsDBMock) {
        this.restCarsDBMock = restCarsDBMock;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<RestCar> find(
            @DefaultValue("100") @QueryParam("limit") int limit,
            @DefaultValue("0") @QueryParam("offset") int offset,
            @DefaultValue("lastModifiedOn") @QueryParam("orderBy") String orderBy,
            @DefaultValue("DESCENDING") @QueryParam("order") SortOrder sortOrder,
            @Context UriInfo uriInfo) {


        final Set<String> params = uriInfo.getQueryParameters().entrySet().stream()
                .filter(p -> !p.getKey().equals("limit"))
                .filter(p -> !p.getKey().equals("offset"))
                .filter(p -> !p.getKey().equals("orderBy"))
                .filter(p -> !p.getKey().equals("order"))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        Optional<Predicate<RestCar>> queryPredicate = params.stream()
                .map(param -> uriInfo.getQueryParameters().get(param).stream()
                        .map(v ->  (Predicate<RestCar>) car -> v.equals(car.undefinedAttributes().get(param)))
                        .reduce(Predicate::or))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(Predicate::and);


        return restCarsDBMock.getCars().entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(queryPredicate.orElse(c -> true))
                .collect(Collectors.toList());

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response findById(@PathParam("id") String id) {
        RestCar restCar = restCarsDBMock.getCars().get(id);
        if (isNull(restCar)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(String.format("Car with id:%s was not found", id))
                    .build();
        }
        else {
            return Response.ok(restCar, MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(RestCar toSave, @Context UriInfo uriInfo) {
        String id = isNull(toSave.getId()) ? UUID.randomUUID().toString() : toSave.getId();
        toSave.setId(id);

        restCarsDBMock.getCars().put(id, toSave);

        return Response.created(uriInfo.getAbsolutePathBuilder().path(id).build()).build();
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") String id) {
        RestCar toDelete = restCarsDBMock.getCars().get(id);

        if(isNull(toDelete)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(String.format("Car with id:%s was not found", id))
                    .build();
        }
        else {
            restCarsDBMock.getCars().remove(toDelete);
            return Response.noContent().build();
        }

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response update(@PathParam("id") String id, RestCar toUpdate) {
        if (restCarsDBMock.getCars().containsKey(id)) {
            restCarsDBMock.getCars().put(id, toUpdate);
            return Response.noContent().build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(String.format("Car with id:%s was not found", id))
                    .build();
        }
    }

}

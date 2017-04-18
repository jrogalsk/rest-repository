package com.jrsoft.learning.testing.remote_repository.car.mocks;

import com.jrsoft.learning.testing.remote_repository.car.entity.RestCar;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Path("resources/count-cars")
public class RestCarsCountExternalResourceMock {
    private final RestCarsDBMock restCarsDBMock;

    public RestCarsCountExternalResourceMock(RestCarsDBMock restCarsDBMock) {
        this.restCarsDBMock = restCarsDBMock;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public long find(@DefaultValue("100") @QueryParam("limit") int limit,
                     @DefaultValue("0") @QueryParam("offset") int offset,
                     @DefaultValue("lastModifiedOn") @QueryParam("orderBy") String orderBy,
                     @DefaultValue("DESCENDING") @QueryParam("order") SortOrder order,
                     @Context UriInfo uriInfo) {
        final Set<String> params = uriInfo.getQueryParameters().entrySet().stream()
                .filter(e -> !e.getKey().equals("limit"))
                .filter(e -> !e.getKey().equals("offset"))
                .filter(e -> !e.getKey().equals("orderBy"))
                .filter(e -> !e.getKey().equals("order"))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        Optional<Predicate<RestCar>> queryPredicate = params.stream()
                .map(param -> uriInfo.getQueryParameters().get(param).stream()
                        .map(v -> (Predicate<RestCar>) car -> v.equals(car.undefinedAttributes().get(param)))
                        .reduce(Predicate::or))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(Predicate::and);

        return restCarsDBMock.getCars().entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(queryPredicate.orElse(c -> true))
                .count();
    }
}

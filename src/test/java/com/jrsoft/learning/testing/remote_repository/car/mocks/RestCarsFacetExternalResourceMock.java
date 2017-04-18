package com.jrsoft.learning.testing.remote_repository.car.mocks;

import com.jrsoft.learning.testing.remote_repository.car.entity.RestCar;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Path("resources/facet-car")
public class RestCarsFacetExternalResourceMock {
    private final RestCarsDBMock restCarsDBMock;

    public RestCarsFacetExternalResourceMock(RestCarsDBMock restCarsDBMock) {
        this.restCarsDBMock = restCarsDBMock;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Long> facet(@QueryParam("attribute") String attribute, @Context UriInfo uriInfo) {
        final Set<String> params = uriInfo.getQueryParameters().entrySet().stream()
                .filter(p -> !p.getKey().equals("attribute"))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        Optional<Predicate<RestCar>> queryPredicate = params.stream()
                .map(param -> uriInfo.getQueryParameters().get(param).stream()
                        .map(v -> (Predicate<RestCar>) car -> v.equals(car.undefinedAttributes().get(param)))
                        .reduce(Predicate::or)
                )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(Predicate::and);

        return restCarsDBMock.getCars().entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(queryPredicate.orElse(c -> true))
                .map(r -> r.undefinedAttributes().get(attribute))
                .filter(Objects::nonNull)
                .map(Objects::toString)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}

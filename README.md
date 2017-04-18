### REST based Repository of entities

This project presents how to use repository of entities exposed by external web service.
Main focus is on implementing Repository interface to use RESTful webservice.

#### Unit testing with local UndertowJaxrsServer
 1. Create mock web-service which reflects web service which is used in production. Here we have 3 mock endpoints in tests package
    * RestCarsExternalResourceMock.java
        * Path: resources/cars/:id
        * Methods: GET DELETE PUT
    * RestCarsCountExternalResourceMock.java
        * Path: resources/count-cars
        * Methods: GET
    * RestCarsFacetExternalResourceMock.java
        * Path: resources/facet-car
        * Methods: GET
 2. Before unit tests, a local web server (UndertowJaxrsServer) is started with above resources exposed.
    * Url of running web service is defined by `TestPortProvider.generateBaseUrl() + "/cars";`
 3. With accessible web service we can run our unit tests

package com.jrsoft.learning.testing.remote_repository.car.control;

import javax.enterprise.inject.Alternative;
import java.util.logging.Logger;

@Alternative
public class TestableRestCarRepository extends RestCarsRepository {

    TestableRestCarRepository() {
        logger = Logger.getLogger(TestableRestCarRepository.class.getName());
    }

}

package jpa.dao;

import jpa.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class StationTest {
    @Autowired
    private StationRepository stationRepository;
}

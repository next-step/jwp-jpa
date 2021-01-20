package jpa.domain;

import jdk.nashorn.internal.ir.annotations.Ignore;
import jpa.repositories.StationRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Ignore
public class Runner implements ApplicationRunner {

    private final StationRepository stationRepository;

    public Runner(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        Station station_expressBusTerminal = stationRepository.save(new Station("고속터미널"));
//
//        station_expressBusTerminal.addLine(new Line("7호선", "올리브색"));
//        station_expressBusTerminal.addLine(new Line("9호선", "골드색"));
//        station_expressBusTerminal.addLine(new Line("3호선", "주황색"));
    }
}

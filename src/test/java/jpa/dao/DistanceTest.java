package jpa.dao;

import jpa.repository.DistanceRepository;
import jpa.repository.LineRepository;
import jpa.repository.StationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DistanceTest {
    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private DistanceRepository distanceRepository;

//    private void insertTransaction(String lineName, String lineColor, String[] stationNames) {
//        Line line = lineRepository.save(new Line(lineName, lineColor));
//        for ( int ix = 0 ; ix < stationNames.length ; ix ++ ) {
//            Station station = getStation(stationNames[ix]);
//            System.out.println(station);
//            distanceRepository.save(new Distance(line, station, ix, ix + 1));
//        }
//    }
//
//    private Station getStation(String stationName) {
//        return stationRepository.findByName(stationName)
//                .orElseGet(() -> stationRepository.save(new Station(stationName)));
//    }
//
//    private void insertValues(String lineName, String lineColor, String[] stationNames) {
//        FutureTask<Void> task = new FutureTask<>(() -> {
//            insertTransaction(lineName, lineColor, stationNames);
//            return null;
//        });
//
//        try {
//            new Thread(task).start();
//            task.get();
//        } catch ( ExecutionException | InterruptedException ignored ) {
//            ignored.printStackTrace();
//        }
//    }

    private void insertValues(String lineName, String lineColor, String[] stationNames) {
        Line line = lineRepository.save(new Line(lineName, lineColor));
        for ( int ix = 0 ; ix < stationNames.length ; ix ++ ) {
            final String stationName = stationNames[ix];
            Station station = stationRepository.findByName(stationNames[ix])
                    .orElseGet(() -> stationRepository.save(new Station(stationName)));
            distanceRepository.save(new Distance(line, station, ix, ix + 1));
        }
    }

    @DisplayName("역 간 거리 구하기")
    @Test
    void calcDistance() {
        insertValues("2호선", "초록", new String[]{"신도림", "대림", "구로디지털단지", "신대방", "신림"});

        Distance departure = distanceRepository.findByLine_NameAndStation_Name("2호선", "대림")
                .orElseGet(Distance::new);
        Distance arrival = distanceRepository.findByLine_NameAndStation_Name("2호선", "신대방")
                .orElseGet(Distance::new);

        Stream<Distance> distanceStream = distanceRepository.findByStationOrderGreaterThanEqualAndStationOrderLessThan(
                departure.getStationOrder(), arrival.getStationOrder());

        Integer totalDistance = distanceStream.mapToInt(Distance::getNextDistance).sum();

        assertThat(totalDistance).isEqualTo(5);
    }

    @DisplayName("환승역 찾기")
    @Test
    void transfer() {
        insertValues("1호선", "파랑", new String[]{"구로", "신도림", "영등포", "신길", "대방"});
        insertValues("2호선", "초록", new String[]{"신도림", "대림", "구로디지털단지", "신대방", "신림"});

        Distance departure = distanceRepository.findByLine_NameAndStation_Name("1호선", "신길")
                .orElseGet(Distance::new);

        System.out.println(departure);

        Distance arrival = distanceRepository.findByLine_NameAndStation_Name("2호선", "신대방")
                .orElseGet(Distance::new);

        System.out.println(arrival);

        Stream<Distance> distanceStream = distanceRepository.findByLine_Name(arrival.getLineName());

        Distance expected = distanceStream.filter(distance -> {
            System.out.println(distance);
            return distanceRepository.findByLine_NameAndStation_Name(departure.getLineName(), distance.getStationName()).isPresent();
        })
                .findFirst()
                .orElseGet(Distance::new);

        assertThat(expected.getStationName()).isEqualTo("신도림");
    }
}

package jpa.com.jaenyeong.domain.mapping;

import jpa.com.jaenyeong.domain.distance.Distance;
import jpa.com.jaenyeong.domain.line.Line;
import jpa.com.jaenyeong.domain.line.LineRepository;
import jpa.com.jaenyeong.domain.station.Station;
import jpa.com.jaenyeong.domain.station.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LineStationRepositoryTest {
    @Autowired
    private LineRepository lines;
    @Autowired
    private StationRepository stations;
    @Autowired
    private LineStationRepository linesAndStations;

    private Station gangnam;
    private Station jamsil;
    private Station yongsan;
    private Station kkachisan;
    private Station mokdong;
    private Station sindorim;
    private Station seocho;
    private Line greenLine;
    private Line blueLine;
    private Line purpleLine;
    private Distance firstDistance;
    private Distance secondDistance;
    private Distance thirdDistance;
    private LineStation gangnamGreenLine;
    private LineStation jamsilGreenLine;
    private LineStation yongsanBlueLine;
    private LineStation kkachisanGreenLine;
    private LineStation kkachisanPurpleLine;

    @BeforeEach
    void setUp() {
        setUpStations();
        setUpLines();
        setUpDistances();
        setUpLineStations();
    }

    private void setUpStations() {
        gangnam = new Station("강남역");
        jamsil = new Station("잠실역");
        yongsan = new Station("용산역");
        kkachisan = new Station("까치산역");
        mokdong = new Station("목동역");
        sindorim = new Station("신도림역");
        seocho = new Station("서초역");

        stations.save(gangnam);
        stations.save(jamsil);
        stations.save(yongsan);
        stations.save(kkachisan);
        stations.save(mokdong);
        stations.save(sindorim);
        stations.save(seocho);
    }

    private void setUpLines() {
        greenLine = new Line("2호선", "green");
        blueLine = new Line("1호선", "blue");
        purpleLine = new Line("5호선", "purple");

        lines.save(greenLine);
        lines.save(blueLine);
        lines.save(purpleLine);
    }

    private void setUpDistances() {
        firstDistance = new Distance(mokdong, 1_000L);
        secondDistance = new Distance(sindorim, 10_000L);
        thirdDistance = new Distance(seocho, 1_000L);
    }

    private void setUpLineStations() {
        gangnamGreenLine = new LineStation(greenLine, gangnam, firstDistance);
        jamsilGreenLine = new LineStation(greenLine, jamsil, firstDistance);
        yongsanBlueLine = new LineStation(blueLine, yongsan, secondDistance);
        kkachisanGreenLine = new LineStation(greenLine, kkachisan, secondDistance);
        kkachisanPurpleLine = new LineStation(purpleLine, kkachisan, thirdDistance);

        linesAndStations.save(gangnamGreenLine);
        linesAndStations.save(jamsilGreenLine);
        linesAndStations.save(yongsanBlueLine);
        linesAndStations.save(kkachisanGreenLine);
        linesAndStations.save(kkachisanPurpleLine);
    }

    @Test
    @DisplayName("노선 조회시 해당 노선에 속한 역의 수 테스트")
    void findByLine() {
        assertAll(
            () -> assertSame(lines.findByName("2호선")
                .orElseThrow(NullPointerException::new)
                .haveStationsSize(), 3),
            () -> assertSame(lines.findByName("1호선")
                .orElseThrow(NullPointerException::new)
                .haveStationsSize(), 1),
            () -> assertSame(lines.findByName("5호선")
                .orElseThrow(NullPointerException::new)
                .haveStationsSize(), 1)
        );
    }

    @Test
    @DisplayName("역 조회시 해당 역이 속한 노선 수 테스트")
    void findByStation() {
        assertAll(
            () -> assertSame(stations.findByName("강남역")
                .orElseThrow(NullPointerException::new)
                .haveLinesSize(), 1),
            () -> assertSame(stations.findByName("용산역")
                .orElseThrow(NullPointerException::new)
                .haveLinesSize(), 1),
            () -> assertSame(stations.findByName("까치산역")
                .orElseThrow(NullPointerException::new)
                .haveLinesSize(), 2)
        );
    }

    @Test
    @DisplayName("노선 조회시 해당 노선에 속한 역명 테스트")
    void linesName() {
        final List<String> stationsName = lines.findByName("2호선")
            .orElseThrow(NullPointerException::new)
            .getStationsName();

        assertSame(stationsName.size(), 3);
        assertSame(stationsName.get(0), "강남역");
        assertSame(stationsName.get(1), "잠실역");
        assertSame(stationsName.get(2), "까치산역");
    }

    @Test
    @DisplayName("역 조회시 해당 역이 속한 노선명 테스트")
    void stationsName() {
        final List<String> linesName = stations.findByName("까치산역")
            .orElseThrow(NullPointerException::new)
            .getLinesName();

        assertSame(linesName.size(), 2);
        assertSame(linesName.get(0), "2호선");
        assertSame(linesName.get(1), "5호선");
    }

    @Test
    @DisplayName("노선 삭제시 매핑된 데이터 삭제 확인 테스트")
    void deleteLine() {
        lines.delete(greenLine);
        final List<LineStation> foundLines = linesAndStations.findByLine(greenLine);

        assertSame(foundLines.size(), 0);
    }

    @Test
    @DisplayName("노선 삭제시 매핑된 데이터 삭제 확인 테스트")
    void deleteStation() {
        stations.delete(gangnam);
        final List<LineStation> foundStations = linesAndStations.findByStation(gangnam);

        assertSame(foundStations.size(), 0);
    }

    @Test
    @DisplayName("거리 데이터 변경 생성 테스트")
    void changeDistance() {
        assertSame(kkachisanGreenLine.getPreviousStationName(), "신도림역");
        assertEquals(kkachisanGreenLine.getDistanceFromPreviousStation(), 10000L);

        final Distance distance = new Distance(mokdong, 100L);
        kkachisanGreenLine.changeDistance(distance);

        assertSame(kkachisanGreenLine.getPreviousStationName(), "목동역");
        assertSame(kkachisanGreenLine.getDistanceFromPreviousStation(), 100L);
    }
}

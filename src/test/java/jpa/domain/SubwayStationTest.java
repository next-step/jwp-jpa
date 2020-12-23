package jpa.domain;

import java.awt.*;

import org.junit.jupiter.api.Test;

import jpa.line.Line;
import jpa.position.Position;
import jpa.station.Station;

class SubwayStationTest {

	@Test
	void createSubwayTest() {

		Station station = new Station("서울역");
		Line line = new Line("1호선", Color.BLUE);
		Position position = new Position(10);

		SubwayStation subway = new SubwayStation(station, line, position);

	}
}

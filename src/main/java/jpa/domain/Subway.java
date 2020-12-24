package jpa.domain;

import jpa.line.Line;
import jpa.position.Position;
import jpa.station.Station;

public class Subway {

	public static void createSubway(Station station, Line line, Position position) {
		station.addLine(line);
		position.addSubway(station, line);
	}
}

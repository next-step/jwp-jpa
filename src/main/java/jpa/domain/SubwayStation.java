package jpa.domain;

import javax.annotation.Resource;

import jpa.line.Line;
import jpa.line.LineRepository;
import jpa.position.Position;
import jpa.position.PositionRepository;
import jpa.station.Station;
import jpa.station.StationRepository;

public class SubwayStation {

	@Resource
	private StationRepository stationRepository;

	@Resource
	private LineRepository lineRepository;

	@Resource
	private PositionRepository positionRepository;

	public SubwayStation(Station station, Line line, Position location) {

		Station newStation = stationRepository.save(station);
		Line newLine = lineRepository.save(line);
		Position position = positionRepository.save(location);

		newStation.addSubway(newLine, position);
	}
}

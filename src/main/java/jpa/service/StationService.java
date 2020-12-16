package jpa.service;

import jpa.domain.Station;
import jpa.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationService {

	@Autowired
	private StationRepository stationRepository;

	public Station getStationByName(String stationName) {
		return stationRepository.findByName(stationName);
	}

	public void saveStations(List<String> stationNames) {
		stationRepository.saveAll(stationNames.stream()
			.map(Station::new)
			.collect(Collectors.toList()));
	}
}

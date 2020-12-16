package jpa.service;

import jpa.domain.Station;
import jpa.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationService {

	@Autowired
	private StationRepository stationRepository;

	public Station getStationByName(String stationName) {
		return stationRepository.findByName(stationName);
	}
}

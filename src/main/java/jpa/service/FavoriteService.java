package jpa.service;

import jpa.domain.Station;
import jpa.dto.FavoriteDto;
import jpa.domain.Favorite;
import jpa.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

	@Autowired
	private FavoriteRepository favoriteRepository;

	@Autowired
	private StationService stationService;

	public List<Favorite> createFavoriteByStation(List<FavoriteDto> favoritesDto) {
		return favoriteRepository.saveAll(favoritesDto.stream()
			.map(favoriteDto -> {
				Station departure = stationService.getStationByName(favoriteDto.getDepartureName());
				Station arrival = stationService.getStationByName(favoriteDto.getArrivalName());
				return new Favorite(departure, arrival);
			}).collect(Collectors.toList()));
	}
}

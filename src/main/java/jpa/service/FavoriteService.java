package jpa.service;

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
				Favorite favorite = new Favorite();
				favorite.setDeparture(stationService.getStationByName(favoriteDto.getDepartureName()));
				favorite.setArrival(stationService.getStationByName(favoriteDto.getArrivalName()));
				return favorite;
			}).collect(Collectors.toList()));
	}
}

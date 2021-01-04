package jpa.repository.step2and3;

import java.util.Arrays;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.entity.Favorite;
import jpa.entity.Line;
import jpa.entity.Member;
import jpa.entity.Station;
import jpa.repository.FavoriteRepository;
import jpa.repository.LineRepository;
import jpa.repository.MemberRepository;
import jpa.repository.StationRepository;

@DataJpaTest
abstract class BeforeEachTest {

	@Autowired
	EntityManager em;

	@Autowired
	private LineRepository lineRepository;

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private FavoriteRepository favoriteRepository;

	@BeforeEach
	void saveBeforeEach() {
		saveStation();
		saveLine();
		saveLineStations();
		saveMember();
		saveFavorite();

		em.flush();
		em.clear();

		System.out.println("\n>> saveBeforeEach 종료\n");
	}

	private void saveStation() {
		stationRepository.saveAll(Arrays.asList(

			// 2, 5호선 환승역
			stationRepository.save(new Station("영등포구청")),
			stationRepository.save(new Station("충정로")),
			stationRepository.save(new Station("을지로4가")),
			stationRepository.save(new Station("동대문역사문화공원")),
			stationRepository.save(new Station("왕십리")),

			// 2호선만
			stationRepository.save(new Station("신촌")),
			stationRepository.save(new Station("잠실나루")),

			// 5호선만
			stationRepository.save(new Station("광화문")),
			stationRepository.save(new Station("강동"))
		));
	}

	private void saveLine() {
		lineRepository.save(new Line("2호선", "초록색"));
		lineRepository.save(new Line("5호선", "보라색"));
	}

	// lineStationRepository를 사용하지 않고, line과 station의 cascade=CascadeType.ALL 옵션을 통한 save
	private void saveLineStations() {
		saveLineStation("2호선", null, "영등포구청", null);
		saveLineStation("2호선", "영등포구청", "신촌", 9);
		saveLineStation("2호선", "신촌", "충정로", 5);
		saveLineStation("2호선", "충정로", "을지로4가", 7);
		saveStationLine("2호선", "을지로4가", "동대문역사문화공원", 1);
		saveStationLine("2호선", "동대문역사문화공원", "왕십리", 5);
		saveStationLine("2호선", "왕십리", "잠실나루", 16);

		saveLineStation("5호선", null, "영등포구청", null);
		saveLineStation("5호선", "영등포구청", "충정로", 14);
		saveLineStation("5호선", "충정로", "광화문", 3);
		saveStationLine("5호선", "광화문", "을지로4가", 4);
		saveStationLine("5호선", "을지로4가", "동대문역사문화공원", 1);
		saveStationLine("5호선", "동대문역사문화공원", "왕십리", 7);
		saveStationLine("5호선", "왕십리", "강동", 15);
	}

	private void saveLineStation(String 노선이름, String 이전역이름, String 역이름, Integer 이전역거리) {
		Line 노선 = lineRepository.findByName(노선이름).get();
		Station 이전역 = stationRepository.findByName(이전역이름);
		Station 역 = stationRepository.findByName(역이름);
		노선.addLineStation(역, 이전역, 이전역거리);
	}

	private void saveStationLine(String 노선이름, String 이전역이름, String 역이름, Integer 이전역거리) {
		Line 노선 = lineRepository.findByName(노선이름).get();
		Station 이전역 = stationRepository.findByName(이전역이름);
		Station 역 = stationRepository.findByName(역이름);
		역.addLineStation(노선, 이전역, 이전역거리);
	}

	private void saveMember() {
		memberRepository.save(new Member(29, "abc@gmail.com", "1234"));
		memberRepository.save(new Member(30, "123@gmail.com", "q1w2e3"));
		memberRepository.save(new Member(31, "qwer@gmail.com", "password"));
		memberRepository.save(new Member(32, "asd@hotmail.com", "imsi"));
	}

	private void saveFavorite() {
		favoriteRepository.saveAll(Arrays.asList(
			createFavorite("abc@gmail.com", "을지로4가", "왕십리"),
			createFavorite("123@gmail.com", "신촌", "잠실나루"),
			createFavorite("123@gmail.com", "왕십리", "강동"),
			createFavorite("qwer@gmail.com", "잠실나루", "강동"),
			createFavorite("qwer@gmail.com", "광화문", "신촌"),
			createFavorite("qwer@gmail.com", "왕십리", "동대문역사문화공원"),
			createFavorite("asd@hotmail.com", "강동", "신촌"),
			createFavorite("asd@hotmail.com", "동대문역사문화공원", "광화문"),
			createFavorite("asd@hotmail.com", "신촌", "충정로"),
			createFavorite("asd@hotmail.com", "광화문", "강동")
		));
	}

	private Favorite createFavorite(String email, String startStationName, String endStationName) {
		return new Favorite(
			memberRepository.findByEmail(email),
			stationRepository.findByName(startStationName),
			stationRepository.findByName(endStationName)
		);
	}
}
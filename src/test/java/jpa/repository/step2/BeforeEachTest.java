package jpa.repository.step2;

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
		saveLineStation();
		saveMember();
		saveFavorite();

		em.flush();
		em.clear();

		System.out.println("\n>> saveBeforeEach 종료\n");
	}

	private void saveStation() {
		stationRepository.saveAll(Arrays.asList(

			// 2, 5호선 환승역
			stationRepository.save(new Station("왕십리")),
			stationRepository.save(new Station("동대문역사문화공원")),
			stationRepository.save(new Station("을지로4가")),
			stationRepository.save(new Station("충정로")),
			stationRepository.save(new Station("영등포구청")),
			stationRepository.save(new Station("까치산")),

			// 2호선만
			stationRepository.save(new Station("잠실나루")),
			stationRepository.save(new Station("신촌")),

			// 5호선만
			stationRepository.save(new Station("광화문")),
			stationRepository.save(new Station("강동"))
		));
	}

	private void saveLine() {
		lineRepository.save(new Line("2호선", "초록색"));
		lineRepository.save(new Line("5호선", "보라색"));
	}

	private void saveLineStation() {
		// 노선과 지하철을 각각 save
		Station 왕십리 = stationRepository.findByName("왕십리");
		Station 충정로 = stationRepository.findByName("충정로");
		Line 이호선 = lineRepository.findByName("2호선").orElseGet(() -> new Line("2호선", "초록색"));
		Line 오호선 = lineRepository.findByName("5호선").orElseGet(() -> new Line("5호선", "보라색"));

		// test : 다양한 경우의 수로 서로를 매핑
		이호선.addStation(왕십리);
		충정로.addLine(이호선);
		왕십리.addLine(오호선);
		오호선.addStation(충정로);

		// 남은 역을 매핑
		stationRepository
			.findAllByNameIn("동대문역사문화공원", "을지로4가", "영등포구청", "까치산", "잠실나루", "신촌")
			.stream()
			.forEach(이호선::addStation);
		stationRepository
			.findAllByNameIn("동대문역사문화공원", "을지로4가", "영등포구청", "까치산", "광화문", "강동")
			.stream()
			.forEach(오호선::addStation);
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
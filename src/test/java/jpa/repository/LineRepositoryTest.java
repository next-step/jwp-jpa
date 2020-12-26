package jpa.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.entity.Line;

@DisplayName("LineRepositoryTest : spring-data-jpa의 Auditing 기능을 통해 자동생성한 날짜 활용 클래스")
@DataJpaTest
class LineRepositoryTest {

	@Autowired
	EntityManager em;

	@Autowired
	private LineRepository lineRepository;

	/**
	 * 저장
	 */
	@DisplayName("insert : spring-data-jpa의 Auditing 기능을 사용하여 createDate 자동 생성 여부 테스트. DB가 아닌 app시간임.")
	@Test
	public void save() {
		lineRepository.save(new Line("2호선", "초록색"));
		assertThat(lineRepository.findAll())
			.allSatisfy(line -> assertThat(line.getCreatedDate()).isNotNull());
	}

	/**
	 * 조회
	 */
	@DisplayName("select : 자동 생성된 createDate를 사용한 질의 테스트")
	@Test
	void findByCreatedDate() {
		lineRepository.save(new Line("2호선", "초록색"));
		lineRepository.save(new Line("3호선", "주황색"));
		List<Line> lines = lineRepository.findByCreatedDateBefore(LocalDateTime.now().plusHours(1));
		assertThat(lines).hasSize(2)
			.anySatisfy(line -> assertThat(line.getColor()).isEqualTo("초록색"))
			.anySatisfy(line -> assertThat(line.getColor()).isEqualTo("주황색"));
	}

	/**
	 * 어노테이션 @Column(updatable = false)가 붙은 변수는 수정하여도 update문에 포함되지 않는다.
	 * 하지만 영속성 컨텍스트에는 남아있기에, clear 전까지는 수정한 값으로 남아있게 된다.
	 */
	@DisplayName("update : updatable false인 컬럼 수정 테스트. 수정이 되면 안된다.")
	@Test
	void update() {
		Line line = lineRepository.save(new Line("2호선", "초록색"));
		line.changeColor("green");

		LocalDateTime beforeModifiedTime = line.getModifiedDate();
		LocalDateTime beforeChangedTime = line.getCreatedDate();
		LocalDateTime afterChangedTime = LocalDateTime.now().minusHours(1);
		line.changeCreatedDate(afterChangedTime);

		// 이 시점에 update쿼리가 날라가게 되며 modifiedDate가 업데이트 된다
		Line beforeClearLine = lineRepository.findByColor("green");
		assertAll(
			() -> assertThat(beforeClearLine.getModifiedDate()).isAfter(beforeModifiedTime),
			() -> assertThat(beforeClearLine.getCreatedDate()).isNotEqualTo(beforeChangedTime),
			() -> assertThat(beforeClearLine.getCreatedDate()).isEqualToIgnoringNanos(afterChangedTime)
		);
		
		em.flush();
		em.clear();
		
		// DB에서 값을 다시 불러온 후에는 createdDate가 update되지 않고 유지되어 있다.
		Line afterClearLine = lineRepository.findByColor("green");
		assertAll(
			() -> assertThat(afterClearLine.getModifiedDate()).isAfter(beforeModifiedTime),
			() -> assertThat(afterClearLine.getCreatedDate()).isEqualToIgnoringNanos(beforeChangedTime),
			() -> assertThat(afterClearLine.getCreatedDate()).isNotEqualTo(afterChangedTime)
		);
	}
}
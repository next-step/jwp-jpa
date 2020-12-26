package jpa.domain;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class LineRepositoryTest {

	@Autowired
	private LineRepository lines;

	@Test
	void findByColor() {
		lines.save(new Line("일호선", Color.BLUE));
		lines.save(new Line("이호선", Color.BLUE));
		lines.save(new Line("삼호선", Color.BLUE));
		lines.save(new Line("사호선", Color.SKYBLUE));

		assertThat(lines.findAllByColor(Color.BLUE)).hasSize(3);
		assertThat(lines.findAllByColor(Color.SKYBLUE)).hasSize(1);
	}

	@Test
	@DisplayName("유니크 키 중복 save 시 예외 테스트")
	void save_duplicatedKey() {
		lines.save(new Line("일호선", Color.BLUE));

		assertThatThrownBy(() -> lines.save(new Line("일호선", Color.BLUE)))
				.isInstanceOf(DataIntegrityViolationException.class)
				.hasCauseInstanceOf(ConstraintViolationException.class);
	}

	@Test
	@DisplayName("createdDate, ModifiedDate 잘 저장되는지 확인")
	void auditing() {
		Line line = lines.save(new Line("일호선", Color.BLUE));

		assertThat(line.getCreatedDate()).isNotNull();
		assertThat(line.getModifiedDate()).isNotNull();
	}
}

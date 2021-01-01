package jpa.repository.step2and3;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import jpa.entity.Member;
import jpa.repository.MemberRepository;

@DisplayName("member, favorite, line, station 모두의 연관관계에 대한 테스트")
@TestPropertySource(properties = "spring.jpa.properties.hibernate.format_sql=true") // 특히 쿼리가 복잡해서 특별히 옵션 삽입
public class TotalRelationshipTest extends BeforeEachTest {
	@Autowired
	private MemberRepository memberRepository;

	@DisplayName("select : select한 결과에서 모든 내용을 꺼내 써도 쿼리는 한번만 실행됨")
	@Test
	void select() {
		List<String> 출발역_이름_목록 = new ArrayList<>();
		List<String> 도착역_이름_목록 = new ArrayList<>();

		// Repository에서 EntityGraph 어노테이션을 사용했기에 모두 join된 쿼리가 한번만 실행됨
		List<Member> 모든_사용자 = memberRepository.findAll();
		모든_사용자.stream()
			.map(Member::getFavorites)
			.forEach(favorites ->
				favorites.stream()
					.forEach(favorite -> {
						// fetch = FetchType.LAZY이지만 매번 쿼리가 실행되지 않음
						출발역_이름_목록.add(favorite.getStartStation().getName());
						도착역_이름_목록.add(favorite.getEndStation().getName());
					}));

		assertAll(
			() -> assertThat(출발역_이름_목록).isNotEmpty(),
			() -> assertThat(도착역_이름_목록).isNotEmpty(),
			() -> assertThat(출발역_이름_목록).hasSameSizeAs(도착역_이름_목록)
		);
	}
}
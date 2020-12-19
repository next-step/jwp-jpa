package jpa.com.jaenyeong.domain;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@DataJpaTest
// 테스트 시 H2 DB를 사용하기 위하여 애노테이션 추가
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// CreateDate, LastModifiedDate 초기화를 위해 추가
@EnableJpaAuditing
public class BaseTest {
}

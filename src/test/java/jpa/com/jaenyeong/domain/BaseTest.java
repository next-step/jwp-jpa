package jpa.com.jaenyeong.domain;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@DataJpaTest
// CreateDate, LastModifiedDate 초기화를 위해 추가
@EnableJpaAuditing
public class BaseTest {
}

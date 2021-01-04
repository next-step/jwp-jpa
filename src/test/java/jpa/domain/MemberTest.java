package jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("이메일, 나이 체크")
    void validateCheck() {
        Member member = Member.of(300,"email", "1234");
        Set<ConstraintViolation<Member>> validations = validator.validate(member);
        assertThat(validations.isEmpty()).isFalse();
        assertThat(validations.size()).isEqualTo(2);
    }
}

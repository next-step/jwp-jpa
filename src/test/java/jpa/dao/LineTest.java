package jpa.dao;

import jpa.repository.LineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LineTest {
    @Autowired
    private LineRepository lineRepository;

    @BeforeEach
    void init() {
        final AtomicInteger atomicInteger = new AtomicInteger(1);
        List<Line> entities = Stream.of("파랑색","초록색","갈색","주황색","보라색")
                .map(color -> new Line(atomicInteger.getAndIncrement() + "호선", color))
                .collect(Collectors.toList());;
        lineRepository.saveAll(entities);
    }

    @DisplayName("이름으로 라인 찾기, select 수행 될 것")
    @Test
    void findByName() {
        Line line = lineRepository.findByName("1호선")
                .orElseGet(Line::new);
        assertThat(line.getName()).isEqualTo("1호선");
    }

    @DisplayName("이름과 색상으로 라인 찾기, select 수행 될 것")
    @Test
    void findByNameAndColor() {
        Line line = lineRepository.findByNameAndColor("1호선", "파랑색")
                .orElseGet(Line::new);
        assertThat(line.getName()).isEqualTo("1호선");
    }

    @DisplayName("업데이트, 실제로 update는 수행 안 될 것, findById는 컨텍스트에서 가져옴")
    @Test
    void update() {
        Line line = lineRepository.findByName("1호선")
                .orElseGet(Line::new);
        assertThat(line.getName()).isEqualTo("1호선");
        line.updateName("9호선");
        assertThat(line.getName()).isEqualTo("9호선");
        Line expected = lineRepository.findById(line.getId())
                .orElseGet(Line::new);
        assertThat(expected.getName()).isEqualTo("9호선");
    }

    @DisplayName("업데이트, 실제 update가 수행 되고 가져 올 것, findByName이라 flush 하고 디비 조회 해옴")
    @Test
    void updateFlush() {
        Line line = lineRepository.findByName("1호선")
                .orElseGet(Line::new);
        assertThat(line.getName()).isEqualTo("1호선");
        line.updateName("9호선");
        Line expected = lineRepository.findByName("9호선")
                .orElseGet(Line::new);
        assertThat(expected.getName()).isEqualTo("9호선");
    }

    @DisplayName("다른 스레드에선 별개의 컨텍스트가 생성 되니 이러면 변경을 감지 못하겠지? 하고 테스트 해보는데 아예 인서트 된 녀석들을 감지 못한다. 이유가 뭐지?")
    @Test
    void updateOtherThread() throws ExecutionException, InterruptedException {
        Line line = lineRepository.findByName("1호선")
                .orElseGet(Line::new);
        assertThat(line.getName()).isEqualTo("1호선");
        line.updateName("9호선");

        ExecutorService executor = Executors.newFixedThreadPool(1);
        boolean isSuccess = executor.submit(() -> {
            Line expected = lineRepository.findByName("2호선")
                    .orElseGet(Line::new);
            System.out.println(lineRepository.count());
            System.out.println(expected.toString());
            return "2호선".equals(expected.getName());
        }).get();
        assertThat(isSuccess).isFalse();
    }
}

package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SectionsTest {
    private Station gangnam = new Station(1L, "gangnam");
    private Station yeoksam = new Station(2L, "yeoksam");
    private Station sunleong = new Station(3L, "sunleong");

    @DisplayName("중복되는 역을 제거하고 Section 컬렉션에 속한 모든 역 목록을 ID순으로 정렬해서 보여준다.")
    @Test
    void getStationsRemovedDupTest() {
        int expectedSize = 3;
        Sections sections = new Sections();
        sections.addSection(new Section(gangnam, yeoksam, 3L));
        sections.addSection(new Section(yeoksam, sunleong, 3L));

        // 중복 제거 확인
        assertThat(sections.getAllStations()).hasSize(expectedSize);
        // 정렬 확인
        assertThat(sections.getAllStations().get(0)).isEqualTo(gangnam);
    }
}
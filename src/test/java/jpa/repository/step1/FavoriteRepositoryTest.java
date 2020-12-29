package jpa.repository.step1;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import jpa.entity.Favorite;
import jpa.repository.FavoriteRepository;

@DisplayName("FavoriteRepositoryTest : 정렬, 페이징 테스트")
@DataJpaTest
class FavoriteRepositoryTest {

	@Autowired
	EntityManager em;

	@Autowired
	FavoriteRepository favoriteRepository;

	/**
	 * CRUD 테스트를 위한 기본적인 rows를 insert
	 */
	@BeforeEach
	void saveBefaoreEach() {
		List<Favorite> initDatas = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			initDatas.add(new Favorite(null, null, null));
		}
		favoriteRepository.saveAll(initDatas);
		em.flush();
	}

	@DisplayName("select : Sort 테스트")
	@Test
	public void sort() {
		List<Favorite> favorites = favoriteRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		List<Long> favoriteIds = favorites.stream().map(Favorite::getId).collect(Collectors.toList());
		System.out.println(favoriteIds);

		// 역순으로 정렬되어 있는지 테스트
		assertThat(favoriteIds).isSortedAccordingTo((favorite1, favorite2) -> (int)(favorite2 - favorite1));
	}

	@DisplayName("select : paging 테스트")
	@Test
	public void paging() {
		int total = favoriteRepository.findAll().size();
		int currentPage = 5;
		int pageSize = 7;

		PageRequest pageRequest = PageRequest.of(5, 7, Sort.by(Sort.Direction.DESC, "id"));
		Page<Favorite> favoritePage = favoriteRepository.findAll(pageRequest);

		assertAll(
			() -> assertThat(favoritePage.getTotalElements()).isEqualTo(total), // 전체 개수
			() -> assertThat(favoritePage.getTotalPages()).isEqualTo(total / pageSize + 1), // 전체 페이지 개수
			() -> assertThat(favoritePage.getNumber()).isEqualTo(currentPage), // 현재 페이지
			() -> assertThat(favoritePage.getNumberOfElements()).isEqualTo(pageSize) // 현재 페이지의 컨텐츠 수
		);
	}
}
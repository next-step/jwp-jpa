package jpa.service;

import jpa.domain.Favorite;
import jpa.domain.Member;
import jpa.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	public void addFavoritesToMember(String memberEmail, List<Favorite> favorites) {
		Member byEmail = this.findMemberByEmail(memberEmail);
		favorites.forEach(byEmail::addFavorite);
	}

	public Member findMemberByEmail(String email) {
		return memberRepository.findByEmail(email);
	}

}

package jpa.domain.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jpa.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@IdClass(MemberFavoriteId.class)
@Table(name = "member_favorite")
public class MemberFavorite extends BaseEntity {

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", insertable = false, updatable = false)
	private Member member;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "favorite_id", insertable = false, updatable = false)
	private Favorite favorite;

	private MemberFavorite(Member member, Favorite favorite) {
		this.member = member;
		this.favorite = favorite;
	}

	public static MemberFavorite create(Member member, Favorite favorite) {
		return new MemberFavorite(member, favorite);
	}
}
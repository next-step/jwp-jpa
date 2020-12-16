package jpa.domain.entity;

import java.io.Serializable;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class MemberFavoriteId implements Serializable {

	private Long member;

	private Long favorite;
}

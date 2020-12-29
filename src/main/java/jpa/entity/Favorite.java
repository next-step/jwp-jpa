package jpa.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Favorite extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "start_station_id")
	private Station startStation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "end_station_id")
	private Station endStation;

	public Long getId() {
		return id;
	}

	protected Favorite() {
	}

	public Favorite(Member member, Station startStation, Station endStation) {
		this.member = member;
		this.startStation = startStation;
		this.endStation = endStation;
	}

	public Member getMember() {
		return member;
	}

	public Station getStartStation() {
		return startStation;
	}

	public Station getEndStation() {
		return endStation;
	}
}
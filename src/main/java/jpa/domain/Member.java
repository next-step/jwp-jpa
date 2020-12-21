package jpa.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Integer age;

	@Column
	private String email;

	@Column(nullable = false)
	private String password;

	@Column
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column
	@UpdateTimestamp
	private LocalDateTime modifiedDate;
}

package jpa.domain;

import com.sun.istack.Nullable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "line")
public class Line extends BaseEntity{
	@Column
	private String color;

	@Column(unique = true)
	private String name;


}

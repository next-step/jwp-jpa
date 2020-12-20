package jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author : byungkyu
 * @date : 2020/12/20
 * @description :
 **/
@Entity
public class Member extends BaseEntity{

	private int age;
	private String email;
	private String password;
}

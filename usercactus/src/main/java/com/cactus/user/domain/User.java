package com.cactus.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	int userId;

	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	protected Type type;

	@Column(name = "id")
	String id;

	@Column(name = "password")
	String password;

	@Column(name = "name")
	String name;

	@Column(name = "tel")
	String tel;

	@Column(name = "is_use")
	String isUse;

	@Column(name = "is_alarm")
	String isAlarm;

	@Column(name = "zip_code")
	String zipCode;

	@Column(name = "addr_main")
	String addrMain;

	@Column(name = "addr_sub")
	String addrSub;

	@Column(name = "email")
	String email;

	@Column(name = "birthday")
	String birthday;

	@Column(name = "gender")
	String gender;

	@Column(name = "profile_url")
	String profileUrl;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "user_id")
	private List<Role> roles;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "user_id")
	private List<Parent> parents;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "user_id")
	private List<Child> children;

	private String token;

	private String refreshToken;

	private String pushToken;

	public User() {
	}

	@Builder
	public User(Type type, String id, String password, String name, String tel, String isUse, String isAlarm, String zipCode, String addrMain, String addrSub, String email, String birthday, String gender, String profileUrl, List<Role> roles, List<Parent> parents, List<Child> children, String token, String refreshToken, String pushToken) {
		this.type = type;
		this.id = id;
		this.password = password;
		this.name = name;
		this.tel = tel;
		this.isUse = isUse;
		this.isAlarm = isAlarm;
		this.zipCode = zipCode;
		this.addrMain = addrMain;
		this.addrSub = addrSub;
		this.email = email;
		this.birthday = birthday;
		this.gender = gender;
		this.profileUrl = profileUrl;
		this.roles = roles;
		this.parents = parents;
		this.children = children;
		this.token = token;
		this.refreshToken = refreshToken;
		this.pushToken = pushToken;
	}
}


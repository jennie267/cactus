package com.cactus.product.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "banner")
public class Banner {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "banner_id")
	private int bannerId;		// 배너ID
	
	@Column(name = "name")
	private String 	name;		// 배너명
	
	@Column(name = "start_time")
	private Date startTime;		// 시작일
	
	@Column(name = "end_time")
	private Date endTime;		// 종료일
	
	@Column(name = "img_url")
	private String 	imgUrl;		// 사진 URL

	@Column(name = "link")
	private String 	link;		// 링크
	
	
}

package com.cactus.product.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prod_id")
	private int prodId;		// 상품ID
	
	@Column(name = "name")
	private String 	name;		// 상품명
	
	@Column(name = "price")
	private Long price;			// 가격
	
	@Column(name = "img_url")
	private String 	imgUrl;		// 사진 URL
	
	@Column(name = "link")
	private String 	link;		// 링크
	
	@Column(name = "is_use")
	private String 	isUse;		// 사용여부
	
	@Column(name = "insert_time")
	private String 	insertTime;	// 등록일자
	
	
	
	
}

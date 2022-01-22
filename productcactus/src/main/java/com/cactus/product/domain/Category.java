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
@Table(name = "category")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "categ_id")
	private int categId;	// 카테고리ID
	
	@Column(name = "name")
	private String 	name;		// 카테고리명
	
	@Column(name = "img_url")
	private String 	imgUrl;		// 사진 URL
	
	@Column(name = "is_use")
	private String 	isUse;		// 사용여부

	@Column(name = "order_no")
	private String 	orderNo;	// 순서
	
	
}

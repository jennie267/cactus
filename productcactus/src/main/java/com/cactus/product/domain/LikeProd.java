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
@Table(name = "like_prod")
public class LikeProd {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "like_id")
	private int likeId;	// 좋아요상품ID
	
	@Column(name = "user_id")
	private int userId;	// 사용자ID
	
	@Column(name = "prod_id")
	private int prodId;	// 상품ID

    @Column(name = "insert_time")
    private Date insertTime;





}

package com.cactus.product.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@Table(name = "prodcateg")
public class Prodcateg {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prctg_id")
	private int prctgId;	// 연결ID
	
	@Column(name = "categ_id")
	private int categId;	// 카테고리ID
	
	@Column(name = "prod_id")
	private int prodId;	// 상품ID

	@Builder
	public Prodcateg(int categId, int prodId) {
		this.categId = categId;
		this.prodId = prodId;
	}
}

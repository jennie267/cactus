package com.cactus.api.message.CactusMessageApi.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Product {
    Long userId;
    Long productId;
    String productName;
    String productPrice;
    String productRemark;
    String productImageUrl;
    Long categoryId;
    String productBannerImageUrl;

    public Product() {
		// TODO Auto-generated constructor stub
	}

    @Builder
    public Product(Long userId, Long productId, String productName, String productPrice, String productRemark, String productImageUrl, Long categoryId, String productBannerImageUrl) {
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productRemark = productRemark;
        this.productImageUrl = productImageUrl;
        this.categoryId = categoryId;
        this.productBannerImageUrl = productBannerImageUrl;
    }
}

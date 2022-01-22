package com.cactus.api.message.CactusMessageApi.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Category {
    Long categoryId;
    String categoryName;
    String categoryImageUrl;

    public Category() {
		// TODO Auto-generated constructor stub
	}

    @Builder
    public Category(Long categoryId, String categoryName, String categoryImageUrl) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImageUrl = categoryImageUrl;
    }
}

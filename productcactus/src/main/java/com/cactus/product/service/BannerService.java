package com.cactus.product.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.cactus.product.domain.Banner;

public interface BannerService {
	List<Banner> findAll(Sort sort);
}

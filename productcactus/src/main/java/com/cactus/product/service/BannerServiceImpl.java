package com.cactus.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cactus.product.domain.Banner;
import com.cactus.product.domain.BannerRepository;

@Service
public class BannerServiceImpl implements BannerService {
	@Autowired
	BannerRepository bannerRepository;
	
	@Override
	public List<Banner> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return bannerRepository.findAll(sort);
	}
}

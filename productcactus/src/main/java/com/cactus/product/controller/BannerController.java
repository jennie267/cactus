package com.cactus.product.controller;

import com.cactus.product.domain.Banner;
import com.cactus.product.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "배너 Controller")
@Controller
@RestController
@RequestMapping("/product/banner")
public class BannerController {
	@Autowired
	private BannerService bannerService;
	

	@ApiOperation(value = "모든 배너 조회")
	@GetMapping("")
	public Map bannerList() {
		Sort sort = Sort.by(Sort.Direction.ASC, "startTime");
		
		List<Banner> banners = bannerService.findAll(sort);
		Map<String, List<Banner>> bannerMap = new HashMap<>();

		bannerMap.put("banners", banners);

		return bannerMap;
	}


}

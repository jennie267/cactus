package com.cactus.product.controller;

import com.cactus.product.domain.Category;
import com.cactus.product.service.CategoryService;
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

@Api(value = "카테고리 Controller")
@Controller
@RestController
@RequestMapping("/product/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	

	@ApiOperation(value = "모든 카테고리 조회")
	@GetMapping("")
	public Map categList() {
		Sort sort = Sort.by(Sort.Direction.ASC, "orderNo");

		List<Category> categories = categoryService.findAll(sort);
		Map<String, List<Category>> categoriesMap = new HashMap<>();

		categoriesMap.put("categories", categories);

		return categoriesMap;
	}


}

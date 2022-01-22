package com.cactus.product.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.cactus.product.domain.LikeProd;
import com.cactus.product.domain.Prodcateg;
import com.cactus.product.domain.Product;
import com.cactus.product.service.BannerService;
import com.cactus.product.service.LikeProdService;
import com.cactus.product.service.ProdcategService;
import com.cactus.product.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value = "상품 Controller")
@Controller
@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProdcategService prodcategService;
	@Autowired
	private LikeProdService likeProdService;
	@Autowired
	private BannerService bannerService;
	
	@ApiOperation(value = "모든 상품 조회")
	@GetMapping("")
	public Map productList() {
		Sort sort = Sort.by(Sort.Direction.DESC, "insertTime");

		List<Product> products = productService.findAll(sort);
		Map<String, List<Product>> productsMap = new HashMap<>();

		productsMap.put("products", products);

		return productsMap;
	}
	
	@ApiOperation(value = "상품 검색")
	@GetMapping("/search/{searchStr}")
	public List<Product> productList(@PathVariable String searchStr) {
		
		return productService.findBySearch(searchStr);
	}

	@ApiOperation(value = "상품 ID로 상품 조회")
	@GetMapping("/{id}")
	public Optional<Product> getProdById(@PathVariable int id) {
		Optional<Product> product = productService.findByProdId(id);
		if(product == null) {
			throw new RuntimeException("상품번호 :   "+ id +" 없습니다.");
		}
		return product;
	}
	
	@ApiOperation(value = "한달동안 인기상품")
	@GetMapping("/month")
	public Map getLikeForMonth() {
		
		List<Integer> products = productService.findForMonth();
		
		Iterable<Integer> ids = products;


		List<Product> productList = productService.findAllById(ids);
		Map<String, List<Product>> productsMap = new HashMap<>();

		productsMap.put("products", productList);

		return productsMap;
	}
	
	@ApiOperation(value = "사용자의 좋아요 상품 검색")
	@GetMapping("/like/{userId}")
	public Map likeList(@PathVariable int userId) {
		List<LikeProd> likes = likeProdService.findByUserIdOrderByInsertTimeDesc(userId);
		List<Integer> ids = new ArrayList<Integer>();
		for (LikeProd like : likes) {
			ids.add(like.getProdId());
		}

		List<Product> productList = productService.findAllById(ids);
		Map<String, List<Product>> productsMap = new HashMap<>();

		productsMap.put("products", productList);

		return productsMap;

	}
	
	@ApiOperation(value = "한 카테고리에 딸린 상품 목록")
	@GetMapping("/category/{categId}")
	public Map prodByCateg(@PathVariable int categId) {
		List<Prodcateg> prodCateg = prodcategService.findByCategId(categId);
		List<Integer> ids = new ArrayList<Integer>();
		for (Prodcateg pg : prodCateg) {
			ids.add(pg.getProdId());
		}

		List<Product> productList = productService.findAllById(ids);
		Map<String, List<Product>> productsMap = new HashMap<>();

		productsMap.put("products", productList);

		return productsMap;

	}
	
	@ApiOperation(value = "이미지 호출")
	@GetMapping(
			value = "/img/{imgName}",
			produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody byte[] getImg(@PathVariable String imgName) throws IOException {
		URL url = getClass().getClassLoader().getResource("img/"+imgName);
		InputStream in = url.openStream();
		
	    return IOUtils.toByteArray(in);
	}

	@PostMapping(value = "/categ/add/{categId}")
	public String addProductOfCategory(@PathVariable int categId, @RequestBody Product product){
		productService.addProductOfCategory(categId, product);
		return "SUCCESS";
	}

}

package com.cactus.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import com.cactus.product.domain.Product;

public interface ProductService {
    Optional<Product> findByProdId(int prodId);

	List<Product> findAll(Sort sort);
	
	List<Product> findAllById(Iterable<Integer> ids);
	
    List<Product> findBySearch(@Param("searchStr") String searchStr);

	List<Integer> findForMonth();

	String addProductOfCategory(int categId, Product product);
	
}

package com.cactus.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;

import com.cactus.product.domain.Category;

public interface CategoryService {
    Optional<Category> findByCategId(int categId);

	List<Category> findAll(Sort sort);
}

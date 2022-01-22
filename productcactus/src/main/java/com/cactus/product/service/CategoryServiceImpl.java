package com.cactus.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cactus.product.domain.Category;
import com.cactus.product.domain.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	
    @Override
    public Optional<Category> findByCategId(int categId) {
        return categoryRepository.findByCategId(categId);
    }

	@Override
	public List<Category> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return categoryRepository.findAll(sort);
	}
}

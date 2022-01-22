package com.cactus.product.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.cactus.product.domain.Prodcateg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cactus.product.domain.Product;
import com.cactus.product.domain.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProdcategService prodcategService;
	
    @Override
    public Optional<Product> findByProdId(int prodId) {
        return productRepository.findByProdId(prodId);
    }

	@Override
	public List<Product> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return productRepository.findAll(sort);
	}

	@Override
	public List<Product> findAllById(Iterable<Integer> ids) {
		// TODO Auto-generated method stub
		return productRepository.findAllById(ids);
	}

	@Override
	public List<Product> findBySearch(String searchStr) {
		// TODO Auto-generated method stub
		return productRepository.findBySearch(searchStr);
	}
	
	@Override
	public List<Integer> findForMonth() {
		return productRepository.findForMonth();
	}

	@Override
	public String addProductOfCategory(int categId, Product product)  {
		//상품 넣기.
		product.setInsertTime(LocalDateTime.now().toString());
		Product newProduct = productRepository.save(product);

		Prodcateg newProdcateg = Prodcateg.builder()
				.categId(categId)
				.prodId(newProduct.getProdId())
				.build();
		//카테고리상품 넣기.
		prodcategService.save(newProdcateg);

		return "SUCCESS";
	}
}

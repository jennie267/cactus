package com.cactus.product.service;

import java.util.List;
import java.util.Optional;

import com.cactus.product.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cactus.product.domain.Prodcateg;
import com.cactus.product.domain.ProdcategRepository;

@Service
public class ProdcategServiceImpl implements ProdcategService {
	@Autowired
	ProdcategRepository prodcategRepository;

	@Override
	public Optional<Prodcateg> findByPrctgId(int prctgId) {
		return prodcategRepository.findByPrctgId(prctgId);
	}

	@Override
	public List<Prodcateg> findByCategId(int categId) {
		return prodcategRepository.findByCategId(categId);
	}

	@Override
	public List<Prodcateg> findByProdId(int prodId) {
		return prodcategRepository.findByProdId(prodId);
	}

	@Override
	public List<Prodcateg> findAll() {
		return prodcategRepository.findAll();
	}

	@Override
	public Optional<Prodcateg> save(Prodcateg prodcateg) {
		return Optional.of(prodcategRepository.save(prodcateg));
	}
}

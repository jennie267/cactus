package com.cactus.product.service;

import java.util.List;
import java.util.Optional;

import com.cactus.product.domain.Prodcateg;
import com.cactus.product.domain.Product;

import javax.swing.text.html.Option;

public interface ProdcategService {
    Optional<Prodcateg> findByPrctgId(int prctgId);
    List<Prodcateg> findByCategId(int categId);
    List<Prodcateg> findByProdId(int prodId);

	List<Prodcateg> findAll();
	Optional<Prodcateg> save(Prodcateg prodcateg);

}

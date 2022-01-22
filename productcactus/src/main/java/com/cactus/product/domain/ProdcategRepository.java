package com.cactus.product.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdcategRepository extends JpaRepository<Prodcateg, Integer> {

    Optional<Prodcateg> findByPrctgId(int prctgId);
    List<Prodcateg> findByCategId(int categId);
    List<Prodcateg> findByProdId(int prodId);

}

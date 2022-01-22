package com.cactus.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.cactus.product.domain.LikeProd;

public interface LikeProdService {
    Optional<LikeProd> findByLikeId(int likeId);
    List<LikeProd> findByUserIdOrderByInsertTimeDesc(int userId);
    List<LikeProd> findByProdId(int prodId);

	List<LikeProd> findAll();
	
    Optional<LikeProd> save(LikeProd likeProd);

    @Transactional
    void deleteByLikeId(int likeId);
    
    @Transactional
    void deleteByUserIdAndProdId(int userId, int prodId);
    
    int checkByUserIdAndProdId(LikeProd likeProd);
}

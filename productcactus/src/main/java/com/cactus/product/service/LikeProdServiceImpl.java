package com.cactus.product.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cactus.product.domain.LikeProd;
import com.cactus.product.domain.LikeProdRepository;

@Service
public class LikeProdServiceImpl implements LikeProdService {
	@Autowired
	LikeProdRepository likeProdRepository;

	@Override
	public Optional<LikeProd> findByLikeId(int likeId) {
		return likeProdRepository.findByLikeId(likeId);
	}

	@Override
	public List<LikeProd> findByUserIdOrderByInsertTimeDesc(int userId) {
		return likeProdRepository.findByUserIdOrderByInsertTimeDesc(userId);
	}

	@Override
	public List<LikeProd> findByProdId(int prodId) {
		return likeProdRepository.findByProdId(prodId);
	}

	@Override
	public List<LikeProd> findAll() {
		return likeProdRepository.findAll();
	}

	@Override
	public Optional<LikeProd> save(LikeProd likeProd) {
		likeProd.setInsertTime(new Date());
		return Optional.of(likeProdRepository.save(likeProd));
	}

	@Override
	 @Transactional
	public void deleteByLikeId(int likeId) {
		likeProdRepository.deleteByLikeId(likeId);		
	}

	@Override
	 @Transactional
	public void deleteByUserIdAndProdId(int userId, int prodId) {
		likeProdRepository.deleteByUserIdAndProdId(userId,prodId);
	}

	@Override
	public int checkByUserIdAndProdId(LikeProd likeProd) {
		// TODO Auto-generated method stub
		System.out.println("likeProd.getProdId() : " + likeProd.getProdId());
		return likeProdRepository.test(likeProd.getUserId(), likeProd.getProdId());
	}


	
}

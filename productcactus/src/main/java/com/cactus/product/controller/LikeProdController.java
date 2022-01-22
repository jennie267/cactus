package com.cactus.product.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.cactus.product.domain.LikeProd;
import com.cactus.product.service.LikeProdService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "상품 좋아요 Controller")
@Controller
@RestController
@RequestMapping("/product/like")
public class LikeProdController {
	@Autowired
	private LikeProdService likeProdService;
	
	@ApiOperation(value = "모든 좋아요리스트 조회")
	@GetMapping("")
	public List<LikeProd> likeList() {
		
		return likeProdService.findAll();
	}

	@ApiOperation(value = "좋아요 추가")
    @PostMapping
    public Optional<LikeProd> newLikeProd(@RequestBody LikeProd likeProd){
        return likeProdService.save(likeProd);
    }
	
	@ApiOperation(value = "좋아요 삭제 (아이디로)")
    @DeleteMapping("/id")
    public String deleteLikeById(@RequestBody int likeId){
		likeProdService.deleteByLikeId(likeId);
        return "SUCCESS";
    }
	
	@ApiOperation(value = "좋아요 삭제 (유저, 상품 ID로)")
    @DeleteMapping(value = "/{userId}/{prodId}")
    public String deleteByUserIdAndProdId(@PathVariable int userId, @PathVariable int prodId){
		likeProdService.deleteByUserIdAndProdId(userId, prodId);
        return "SUCCESS";
    }
	
	@ApiOperation(value = "좋아요 체크")
    @PostMapping("/check")
    public boolean checkByUserIdAndProdId(@RequestBody LikeProd likeProd){
		int cnt = likeProdService.checkByUserIdAndProdId(likeProd);
        if (cnt >0) return true;
        else return false;
    }

}

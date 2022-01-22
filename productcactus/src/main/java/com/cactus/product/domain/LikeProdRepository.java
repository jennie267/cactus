package com.cactus.product.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LikeProdRepository extends JpaRepository<LikeProd, Integer> {

    Optional<LikeProd> findByLikeId(int likeId);
    List<LikeProd> findByUserIdOrderByInsertTimeDesc(int userId);
    List<LikeProd> findByProdId(int prodId);

//    @Modifying
//    @Transactional
//    @Query(nativeQuery = true, value= "delete from like_prod a where a.like_id=:likeId")
//    void deleteByLikeId(Long likeId);
    
    void deleteByLikeId(int likeId);
    
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value= "delete from like_prod where user_id=:userId and prod_id=:prodId")
    void deleteByUserIdAndProdId(@Param("userId") int userId, @Param("prodId") int prodId);
    
    @Query(nativeQuery = true, value= "select count(*) as cnt from like_prod where user_id= :userId and prod_id=:prodId")
    int test(@Param("userId") int userId, @Param("prodId") int prodId);
    


}

package com.cactus.product.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByProdId(int prodId);
    
    @Query(nativeQuery = true, value= "select * from product where name like CONCAT('%',:searchStr,'%') ")
    List<Product> findBySearch(@Param("searchStr") String searchStr);
    
    @Query(nativeQuery = true, value= "select prod_id from like_prod where insert_time <= now() and insert_time >= DATE_SUB(  now(),  INTERVAL 1 MONTH  ) group by prod_id order by count(*) desc ")
    List<Integer> findForMonth();
    

}

package com.cactus.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>{

	//userId로 유저 정보 조회
	User findByUserId(int userId);

	List<User> findByIdContaining(String id);

	//id 로 유저 정보 조회
	User findById(String id);

	//userId 로 유저정보 삭제
	void deleteByUserId(int userId);

	//id, password로 유저 정보 조회
	User findByIdAndPassword(String id, String password );

	String findPasswordById(String id);

	@Query(nativeQuery = true, value= "select * from user where user_id in (select child_user_id from child where user_id = :userId) order by birthday asc ")
	List<User> findChildren(@Param("userId") int userId);

	@Query(nativeQuery = true, value= "select * from user where user_id in (select parent_user_id from parent where user_id = :userId) order by birthday asc ")
	List<User> findParents(@Param("userId") int userId);

}

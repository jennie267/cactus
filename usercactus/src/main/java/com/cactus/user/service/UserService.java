package com.cactus.user.service;

import com.cactus.user.domain.User;
import com.cactus.user.domain.dto.ChildBookDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService  {

	//유저 전체조회
	List<User> findAll();
	//유저 ID 검섹
	List<User> findByIdContaining(String id);

	//유저 생성
	Optional<User> createUser(User user);
	//유저 수정
	Optional<User> updateUser(User user);

	//userId로 유저 정보 조회
	User findByUserId(int userId);

	//id 로 유저 정보 조회
	User findById(String id);

	//userId 로 유저정보 삭제
	void deleteByUserId(int userId);

	User login(String id, String password );

	User findByIdAndPassword(String id, String password);

	List<User> findChildren(int id);

	List<User> findParents(int id);

	User addChild(int id, int childId);

	User addParent(int id, int parentId);

	User saveProfile(int userId, MultipartFile multipartFile);

	int findIdCheck(String id);

	List<ChildBookDTO> findChildBook(int id, String authHeader);

	List<User> delMyChild(int parentId, int childId);
	List<User> delMyParent(int parentId, int childId);

}
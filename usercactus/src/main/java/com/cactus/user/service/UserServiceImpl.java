package com.cactus.user.service;

import com.cactus.user.domain.*;
import com.cactus.user.domain.dto.ChildBookDTO;
import com.cactus.user.domain.dto.MessageDTO;
import com.cactus.user.util.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	FileService fileService;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public List<User> findByIdContaining(String id) {
		return userRepository.findByIdContaining(id);
	}

	@Override
	public Optional<User> createUser(User user) {

		//일단 Role은 멤버로 넣음.
		List<Role> roles = new ArrayList<>();
		roles.add(new Role(RoleType.MEMBER));
		user.setRoles(roles);

		//비밀번호 변환하여 저장
		user.setPassword(encoder.encode(user.getPassword()));

		return Optional.of(userRepository.save(user));
	}

	@Override
	public Optional<User> updateUser(User user) {

		user.setPassword(encoder.encode(user.getPassword()));

		return Optional.of(userRepository.save(user));
	}

	@Override
	public User findByUserId(int userId) {
		return userRepository.findByUserId(userId);
	}

	@Override
	public User findById(String id) {
		return userRepository.findById(id);
	}

	@Override
	public void deleteByUserId(int userId) {
		userRepository.deleteByUserId(userId);
	}

	@Override
	public User login(String id, String password) {

		User user = userRepository.findById(id);

		if (user == null) return null;

		if(!encoder.matches(password, user.getPassword())) return null;

		ResponseEntity<Map> result = RestTemplateUtil.getToken(user.getId(), user.getPassword());

		if(result==null) return null;

		user.setToken(result.getBody().get("token").toString());
		user.setRefreshToken(result.getBody().get("refreshToken").toString());

		return user;
	}

	@Override
	public User findByIdAndPassword(String id, String password) {
		return userRepository.findByIdAndPassword(id, password);
	}

	@Override
	public List<User> findChildren(int id) {
/*		User user = userRepository.findByUserId(id);
		List<Child> children = user.getChildren();

		List<User> users = new ArrayList<>();
		if (children != null && children.size()>0){
			for (Child child:children) {
				User childUser = userRepository.findByUserId(child.getChildUserId());
				childUser.setPassword(null);
				users.add(childUser);
			}
		}

		return users;*/
		List<User> users = userRepository.findChildren(id);
		if (users != null && users.size()>0){
			for (User user:users) {
				user.setPassword(null);
			}
		}

		return users;
	}

	@Override
	public List<User> findParents(int id) {
		/*User user = userRepository.findByUserId(id);
		List<Parent> parents = user.getParents();

		List<User> users = new ArrayList<>();
		if (parents != null && parents.size()>0){
			for (Parent parent:parents) {
				User parentUser = userRepository.findByUserId(parent.getParentUserId());
				parentUser.setPassword(null);
				users.add(parentUser);
			}
		}

		return users;*/

		List<User> users = userRepository.findParents(id);
		if (users != null && users.size()>0){
			for (User user:users) {
				user.setPassword(null);
			}
		}

		return users;
	}

	@Override
	public User addChild(int id, int childId) {
		//부모의 자식 리스트 검사.
		User user = userRepository.findByUserId(id);
		List<Child> children = user.getChildren();

		boolean isExists =false;

		if(children.size()==0){
			Child newChild = new Child(childId);
			user.getChildren().add(newChild);

			userRepository.save(user);
		}else{
			for (Child child:children) {
				if(childId == child.getChildUserId()) isExists = true;
			}
			if(!isExists){
				Child newChild = new Child(childId);
				user.getChildren().add(newChild);

				userRepository.save(user);
			}
		}


		return user;
	}

	@Override
	public User addParent(int id, int parentId) {
		//자신의 부모 리스트 검사
		User user  = userRepository.findByUserId(id);
		List<Parent> parents = user.getParents();

		boolean isExists = false;

		if(parents.size() == 0){
			Parent newParent = new Parent(parentId);
			user.getParents().add(newParent);

			userRepository.save(user);
		}else{
			for(Parent parent:parents){
				if(parentId == parent.getParentUserId()) isExists = true;
			}
			if(!isExists){
				Parent newParent = new Parent(parentId);
				user.getParents().add(newParent);

				userRepository.save(user);
			}
		}
		return user;
	}

	@Override
	public User saveProfile(int userId, MultipartFile multipartFile) {
		//파일 저장
		String newFileName = UUID.randomUUID().toString();
		String profileUrl = fileService.profileUpload(userId,newFileName, multipartFile);

		//유저정보에 profile_url update
		User user = userRepository.findByUserId(userId);
		user.setProfileUrl(profileUrl);
		userRepository.save(user);

		return user;
	}
	
	@Override
	public int findIdCheck(String id) {
		User user = userRepository.findById(id);
		int count;
		if(user!=null) {
			count = 1;
		} else 
			count = 0;
		return count;
	}

	@Override
	public List<ChildBookDTO> findChildBook(int id, String authHeader) {
		List<ChildBookDTO> childBookDTOS = new ArrayList<>();

		//자식 리스트
		List<Child> children = userRepository.findByUserId(id).getChildren();
		//메세지 리스트
		for (Child child:children) {
			MessageDTO messageDTO = RestTemplateUtil.getLastMessageFromChild(id,child.getChildUserId(),authHeader).getBody();
			User user = userRepository.findByUserId(child.getChildUserId());

			ChildBookDTO childBookDTO = ChildBookDTO.builder()
					.userId(id)
					.childId(child.getChildUserId())
					.childImageUrl(user.getProfileUrl())
					.childName(user.getName())
					.childPhone(user.getTel())
					.lastMessage(messageDTO==null?"":messageDTO.getContents())
					.lastMessageTime(messageDTO==null? LocalDateTime.now() :messageDTO.getInsertTime())
					.build();

			childBookDTOS.add(childBookDTO);
		}

		return childBookDTOS;
	}

	@Override
	public List<User> delMyChild(int parentId, int childId) {
		User parentUser = userRepository.findByUserId(parentId);
		User childUser = userRepository.findByUserId(childId);

		Child delChild = null;
		for(Child child : parentUser.getChildren()){
			if(child.getChildUserId() == childUser.getUserId()){
				delChild = child;
			}
		}
		if(delChild!=null){
			parentUser.getChildren().remove(delChild);
			userRepository.save(parentUser);
		}

		Parent delParent = null;
		for(Parent parent : childUser.getParents()){
			if(parent.getParentUserId() == parentUser.getUserId()){
				delParent = parent;
			}
		}
		if(delParent!=null){
			childUser.getParents().remove(delParent);
			userRepository.save(childUser);
		}

		List<User> children = new ArrayList<>();
		for(Child child : parentUser.getChildren()){
			User user = userRepository.findByUserId(child.getChildUserId());
			children.add(user);
		}

		return children;
	}
	@Override
	public List<User> delMyParent(int parentId, int childId) {
		User parentUser = userRepository.findByUserId(parentId);
		User childUser = userRepository.findByUserId(childId);

		Child delChild = null;
		for(Child child : parentUser.getChildren()){
			if(child.getChildUserId() == childUser.getUserId()){
				delChild = child;
			}
		}
		if(delChild!=null){
			parentUser.getChildren().remove(delChild);
			userRepository.save(parentUser);
		}

		Parent delParent = null;
		for(Parent parent : childUser.getParents()){
			if(parent.getParentUserId() == parentUser.getUserId()){
				delParent = parent;
			}
		}
		if(delParent!=null){
			childUser.getParents().remove(delParent);
			userRepository.save(childUser);
		}

		List<User> parents = new ArrayList<>();
		for(Parent parent : childUser.getParents()){
			User user = userRepository.findByUserId(parent.getParentUserId());
			parents.add(user);
		}

		return parents;
	}
}

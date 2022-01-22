package com.cactus.user.controller;

import com.cactus.user.domain.User;
import com.cactus.user.domain.dto.ChildBookDTO;
import com.cactus.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "로그인")
	@GetMapping("/login/{id}/{password}")
	public User login(@PathVariable String id, @PathVariable String password) {
		User user = userService.login(id, password);

		if(user == null) System.out.println(" 로그인 실패 ");

	    return user;
	}
    //테스트 다시
	@ApiOperation(value = "로그아웃")
    @PostMapping("/logout")
    public String logout() throws Exception {
//    	session.invalidate();
//    	session.removeAttribute("userInfo");
		return "";
	}

	@ApiOperation(value = "모든 회원정보 조회")
	@GetMapping("/all")
	public List<User> getUsers(){
		return userService.findAll();
	}

	@ApiOperation(value = "회원 ID like 조회")
	@GetMapping("/like/{id}")
	public Map findByIdLike(@PathVariable String id){
		List<User> users = userService.findByIdContaining(id);

		Map<String, List<User>> usersMap = new HashMap<>();
		usersMap.put("users", users);

		return usersMap;
	}

	@ApiOperation(value = "회원정보 조회")
    @GetMapping("/user-id/{userId}")
    public User getUser(@PathVariable int userId) {
		return userService.findByUserId(userId);
    }

	@ApiOperation(value = "회원정보 삭제")
	@DeleteMapping("/user-id/{userId}")
	public String deleteUser(@PathVariable int userId){
		userService.deleteByUserId(userId);
		return "SUCCESS";
	}

	@ApiOperation(value = "회원정보 추가")
    @PostMapping("/signup")
	public String createUser(@RequestBody User user){
    	userService.createUser(user);
		return "SUCCESS";
	}

	@ApiOperation(value = "회원정보 수정")
	@PostMapping("/update")
	public User updateUser(@RequestBody User user){
		return userService.updateUser(user).get();
	}

	@ApiOperation(value = "푸시토큰 수정")
	@PostMapping("/push/{id}")
	public User updatePushToken(@PathVariable int id, @RequestBody Map<String, Object> param){
		User user = userService.findByUserId(id);
		String token = (String) param.get("token");
		user.setPushToken(token);

		return userService.updateUser(user).get();
	}

	@ApiOperation(value = "자식 리스트")
	@GetMapping("/children/{id}")
	public Map findChildren(@PathVariable int id){

		List<User> children = userService.findChildren(id);

		Map<String, List<User>> childrenMap = new HashMap<>();
		childrenMap.put("users", children);

		return childrenMap;
	}

	@ApiOperation(value = "부모 리스트")
	@GetMapping("/parents/{id}")
	public Map findParents(@PathVariable int id){

		List<User> parents = userService.findParents(id);

		Map<String, List<User>> parentsMap = new HashMap<>();
		parentsMap.put("users", parents);

		return parentsMap;
	}

	@ApiOperation(value = "자식 추가")
	@PostMapping("/child/{id}/{childId}")
	public User addChild(@PathVariable int id, @PathVariable int childId){

		User user = userService.addChild(id, childId);
		return user;
	}

	@ApiOperation(value = "부모 추가")
	@PostMapping("/parent/{id}/{parentId}")
	public User addParent(@PathVariable int id, @PathVariable int parentId){

		User user = userService.addParent(id, parentId);
		return user;
	}

	//김정우 테스트용
	@GetMapping("/{id}/{password}")
	public User findByUserIdAndPassword(@PathVariable String id, @PathVariable String password) {
		return userService.findByIdAndPassword(id, password);
	}

	//김정우 테스트용
	@GetMapping("/id/{id}")
	public User findById(@PathVariable String id) {
		return userService.findById(id);
	}

	@PostMapping("/profile/{userId}")
	public User saveProfile(@PathVariable int userId, @RequestParam("file") MultipartFile multipartFile){
		User user = userService.saveProfile(userId, multipartFile);
		return user;
	}
	
	//아이디 중복확인
	@GetMapping("/idcheck/{id}")
	public int idCheck(@PathVariable String id) {
		int count = userService.findIdCheck(id);
		return count;
	}

	@GetMapping(value = "/child-book/{id}")
	public Map findChildBook(@PathVariable int id, @RequestHeader(value = "authorization", required = false) String authHeader){

		List<ChildBookDTO> children = userService.findChildBook(id,authHeader);

		Map<String, List<ChildBookDTO>> childrenMap = new HashMap<>();
		childrenMap.put("childBooks", children);

		return childrenMap;
	}

	@PostMapping(value = "/del/child/{parentId}/{childId}")
	public Map delMyChild(@PathVariable int parentId,@PathVariable int childId ){
		List<User> children = userService.delMyChild(parentId, childId);

		Map<String, List<User>> childrenMap = new HashMap<>();
		childrenMap.put("users", children);

		return childrenMap;
	}


	@PostMapping(value = "/del/parent/{parentId}/{childId}")
	public Map delMyParent(@PathVariable int parentId,@PathVariable int childId ){
		List<User> parents = userService.delMyParent(parentId, childId);

		Map<String, List<User>> parentsMap = new HashMap<>();
		parentsMap.put("users", parents);

		return parentsMap;
	}

}
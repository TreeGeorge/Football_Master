package com.newdeal.footballMaster.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.newdeal.footballMaster.model.User;
import com.newdeal.footballMaster.model.UserPaymentHistory;
import com.newdeal.footballMaster.service.UserPaymentHistoryService;
import com.newdeal.footballMaster.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserPaymentHistoryService userPaymentHistoryService;
	
	// 단일 유저 정보 호출
	@RequestMapping(value="/user" , method = RequestMethod.GET)
	public User getUser(
			@RequestHeader("accessToken") String accessToken) {
		
		String email = userService.checkToken(accessToken);
		
		if (email == null) {	// 토큰이 유효하지 않을때
			return null;
		} else {
			
			User output = sqlSession.selectOne("UserMapper.selectUser", email);
			
			return output;
			
		}
	}
	
	// 유저 정보 변경
	@RequestMapping(value="/user" , method = RequestMethod.PUT)
	public int updateUser(
			@RequestHeader("accessToken") String accessToken,
			@RequestBody final User user) {
		
		String email = userService.checkToken(accessToken);
		
		if (email == null) {	// 토큰이 유효하지 않을때
			return 0;
		} else {
			
			User input = sqlSession.selectOne("UserMapper.selectUser", email);
			
			if (input.getGender().equals("남성")) {
				input.setGender("M");
			} else {
				input.setGender("F");
			}
			
			if (user.getGender() != null) {
				input.setGender(user.getGender());
			}
		
			if (user.getBirthday() != null) {
				input.setBirthday(user.getBirthday());
			}
			
			if (user.getName() != null) {
				input.setName(user.getName());
			}
			
			if (user.getPhone_number() != null) {
				input.setPhone_number(user.getPhone_number());
			}
			
			sqlSession.update("UserMapper.updateUser", input);
			
			return 1;
		}
	}
	
	// 유저 삭제
	@RequestMapping(value="/user", method = RequestMethod.DELETE)
	public int deleteUser(
			@RequestHeader("accessToken") String accessToken) {
		
		String email = userService.checkToken(accessToken);
		
		if (email == null) {	// 토큰이 유효하지 않을때
			return 0;
		} else {
			
			User input = sqlSession.selectOne("UserMapper.selectUser", email);
			
//			if (input == null) {	// 토큰의 private 키를 탈취당해서 이메일 값을 받았는데 존재하지 않는 유저일때
//				return 2;
//			}
			
			int user_id = input.getId();
			
			sqlSession.delete("UserMapper.deleteUserBank", user_id);
			sqlSession.delete("UserMapper.deleteUserMatch", user_id);
			sqlSession.delete("UserMapper.deleteUserPaymentHistory", user_id);
			sqlSession.delete("UserMapper.deleteUser", user_id);
			
			return 1;
			
		}
	}
	
	// 유저 생성 및 로그인 ( 소셜로그인이 완료된 뒤에 작동 )
	@RequestMapping(value="/user", method = RequestMethod.POST)
	public int createUser(
			HttpSession session,
			@RequestBody final User user) {
		
		String email = user.getEmail();
		// TODO 잘못된 정보 들어왔을때 대응 필요함 프론트에서 하던지 둘다 하던지 일단 작성해야함
		// TODO 구글은 왜 바로불러오는가... 알아야함 ㅠㅠ
		
		if (email == null) {
			return 0;
		} else if (sqlSession.selectOne("UserMapper.selectUser", email) != null){
			userService.creatToken(session, email);
			return 1;
		} else {
			sqlSession.insert("UserMapper.createUser", email);
			userService.creatToken(session, email);
			return 1;
		}
			
	}
	
	// 유저 캐시 사용내역 호출
	@RequestMapping(value="/user/cashHistory", method = RequestMethod.GET)
	public List<UserPaymentHistory> getUserPaymentHistory(
			@RequestHeader("accessToken") String accessToken) {
		
		String email = userService.checkToken(accessToken);
		
		if (email == null) {
			return null;
		}
		
		User user = sqlSession.selectOne("UserMapper.selectUser", email);
		
		int user_id = user.getId();
		
		List<UserPaymentHistory> output = sqlSession.selectList("UserPaymentHistoryMapper.selectUserPaymentHistory", user_id);
		
		return output;
	}
	
	// 유저 캐시 충전
	@RequestMapping(value="/user/cashCharge", method = RequestMethod.POST)
	public int userCashCharge(
			@RequestHeader("accessToken") String accessToken,
			@RequestBody final UserPaymentHistory userPaymentHistory) {
		
		String email = userService.checkToken(accessToken);
		
		if (email == null) {
			return 0;
		}
		
		User user = sqlSession.selectOne("UserMapper.selectUser", email);
		
		int price = userPaymentHistory.getPrice();
		
		UserPaymentHistory input = new UserPaymentHistory();
		
		input.setUser_id(user.getId());
		input.setPrice(price);
		
		sqlSession.insert("UserPaymentHistoryMapper.chargeCash", input);
		sqlSession.update("UserPaymentHistoryMapper.changeUserBalance", input);
		
		return 1;
	}
	



}

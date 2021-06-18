package com.newdeal.footballMaster.dao;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.newdeal.footballMaster.jwt.AccessToken;
import com.newdeal.footballMaster.model.Users;
import com.newdeal.footballMaster.model.UsersCash;
import com.newdeal.footballMaster.service.UsersService;

//import lombok.extern.slf4j.Slf4j;

/** Lombok의 Log4j 객체 */
//import lombok.extern.slf4j.Slf4j;
//@Slf4j
/** JUnit에 의한 테스트 클래스로 정의 */
//import org.junit.runner.RunWith;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
/** Spring의 설정 파일들을 읽어들이도록 설정 (**은 `모든` 이라는 의미) */
//import org.springframework.test.context.ContextConfiguration;
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*-context.xml" })
/** 웹 어플리케이션임을 명시 */
//import org.springframework.test.context.web.WebAppConfiguration;
@WebAppConfiguration
/** 메서드 이름순서로 실행하도록 설정 (설정하지 않을 경우 무작위 순서로 실행됨) */
//import org.junit.FixMethodOrder;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {
	
	/** MyBatis의 SQL세션 주입 설정 */
	// import org.springframework.beans.factory.annotation.Autowired;
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	UsersService userService;
	
//	/** 단일행 조회 테스트 */
//    @Test
//    public void test1() {    
//    	UserPaymentHistory input = new UserPaymentHistory();
//    	User user = sqlSession.selectOne("UserMapper.selectUser", "asdf@naver.com");
//    	input.setBalance(user.getBalance() + 10000);
//    	input.setUser_id(1);
//    	sqlSession.update("UserPaymentHistoryMapper.changeUserBalance", input);
//    }
  
    
	
	/** 토큰 유효성 검사 테스트
	 * @throws UnsupportedEncodingException */
    @Test
    public void test2() throws UnsupportedEncodingException {    
    	
    	AccessToken createToken = new AccessToken();
    	String accessToken = createToken.createToken("asdf@naver.com");
    	System.out.println("엑세스토큰입니다 : " + accessToken);
        
        Map<String, Object> claimMap = createToken.verifyJWT(accessToken);
        
        System.out.println("이게 어딘지 보여줘 : " + claimMap);
        
//        String email = (String) claimMap.get("data");
//        
//        User output = new User();
//        output = userService.getUser(email);
//        
    }
	
 

}

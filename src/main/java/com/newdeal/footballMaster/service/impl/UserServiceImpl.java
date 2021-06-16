package com.newdeal.footballMaster.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newdeal.footballMaster.jwt.AccessToken;
import com.newdeal.footballMaster.model.User;
import com.newdeal.footballMaster.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	/** MyBatis 세션 객체 주입 설정 */
	@Autowired
	SqlSession sqlSession;

	// user의 정보를 가져온다
	@Override
	public User getUser(String email) {

		User result = new User();
        result = sqlSession.selectOne("UserMapper.selectUser", email);
        
        return result;
	}

	// session의 access token 체크
	@Override
	public String checkToken(String accessToken){
		
		AccessToken checkToken = new AccessToken();
		
		Map<String, Object> result;
		try {
			result = checkToken.verifyJWT(accessToken);
			if (result == null) {
				return null;
			} else {
				String email = (String) result.get("data");
				return email;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	// login 성공시 토큰 발급
	@Override
	public void creatToken(HttpSession session, String email) {
		
		AccessToken createToken = new AccessToken();
		String accessToken = createToken.createToken(email);
		
		session.setAttribute("accessToken", accessToken);
		
	}

}

package com.newdeal.footballMaster.service;

import javax.servlet.http.HttpSession;

//import com.newdeal.footballMaster.model.User;

public interface UsersService {
	
	// 세션 토큰 체크
	public String checkToken(String accessToken);
	
	// login시 토큰 발급
	public void creatToken(HttpSession session, String email);

}

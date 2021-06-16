package com.newdeal.footballMaster.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newdeal.footballMaster.service.UserPaymentHistoryService;

@Service
public class UserPaymentHistoryServiceImpl implements UserPaymentHistoryService {
	
	/** MyBatis 세션 객체 주입 설정 */
	@Autowired
	SqlSession sqlSession;

}

package com.newdeal.footballMaster.controllers;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.newdeal.footballMaster.model.Managers;

@RestController
public class ManagerController {
	
	@Autowired
	SqlSession sqlSession;
	
	// 단일 매니저 정보 호출
	@RequestMapping(value="/manager/{id}" , method = RequestMethod.GET)
	public Managers getManager(
			@PathVariable("id") int id) {
		
		Managers output = sqlSession.selectOne("ManagerMapper.selectManager", id);
		
		return output;
	}

}

package com.newdeal.footballMaster.controllers;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.newdeal.footballMaster.model.Matches;

@RestController
public class MatchController {
	
	@Autowired
	SqlSession sqlSession;
	
	//TODO 매퍼 수정<< 필드정보값 뒤져서 해당 area값을 가진 친구들을 가져와야함
	//TODO 지역별 필요
	// 필터링된 매치 정보리스트 가져오기
	@RequestMapping(value="/matches",method=RequestMethod.GET)
	public List<Matches> getMatches(
			@RequestBody Matches params) {
		
		List<Matches> output = sqlSession.selectList("MatchesMapper.selectMatches", params);
		
		return output;
	}
	
	// 단일 매치 정보 가져오기
	@RequestMapping(value="/matches/{id}",method=RequestMethod.GET)
	public Matches getMatch(
			@PathVariable("id") int id) {
		
		Matches output = sqlSession.selectOne("MatchesMapper.selectMatch", id);
		
		return output;
	}
	

}

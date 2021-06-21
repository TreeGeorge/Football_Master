package com.newdeal.footballMaster.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
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
	
	// 필터링된 매치 정보리스트 가져오기
	@RequestMapping(value="/matches",method=RequestMethod.GET)
	public List<Matches> getMatchesFilter() {
		
		Matches input = new Matches();
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String today = format.format(date);
		input.setGender_rule("('M','F','H')");
		input.setLevel("('L','M','H')");
		input.setRegion("A");
		input.setMatch_date(today);
		
		List<Matches> output = sqlSession.selectList("MatchesMapper.selectMatches", input);
		
		return output;
	}
	
	// 필터링된 매치 정보리스트 가져오기
	@RequestMapping(value="/matches",method=RequestMethod.POST)
	public List<Matches> getMatchesFilter(
			@RequestBody Matches params) {
		
		Matches input = new Matches();
		
		String genderRule = "";
		String level = "";
	
		// 다중 값 파싱
		if (params.getGender_rule().contains("M")) {
			genderRule = "('M')";
			if (params.getGender_rule().contains("F")) {
				genderRule = "('M','F')";
				if (params.getGender_rule().contains("H")) {
					genderRule = "('M','F','H')";
				}
			} else if (params.getGender_rule().contains("H")) {
				genderRule = "('M','H')";
			}
		} else if (params.getGender_rule().contains("F")) {
			genderRule = "('F')";
			if (params.getGender_rule().contains("H")) {
				genderRule = "('F','H')";
			}
		} else if (params.getGender_rule().contains("H")) {
			genderRule = "('H')";
		} else {
			genderRule = "('M','F','H')";
		}
		
		input.setGender_rule(genderRule);
		
		// 다중 값 파싱
		if (params.getLevel().contains("L")) {
			level = "('L')";
			if (params.getLevel().contains("M")) {
				level = "('L','M')";
				if (params.getLevel().contains("H")) {
					level = "('L','M','H')";
				}
			} else if (params.getLevel().contains("H")) {
				level = "('L','H')";
			}
		} else if (params.getLevel().contains("M")) {
			level = "('M')";
			if (params.getLevel().contains("H")) {
				level = "('M','H')";
			}
		} else if (params.getLevel().contains("H")) {
			level = "('H')";
		} else {
			level = "('L','M','H')";
		}
		
		input.setLevel(level);
		input.setRegion(params.getRegion());
		input.setMatch_date(params.getMatch_date());
		
		List<Matches> output = sqlSession.selectList("MatchesMapper.selectMatches", input);
		
		return output;
	}
	
	// 레벨별 필터
	@RequestMapping(value="/level/{id}/matches",method=RequestMethod.GET)
	public List<Matches> getLevelMatches(
			@PathVariable("id") String id) {
		
		List<Matches> output = sqlSession.selectList("MatchesMapper.selectLevelMatches", id);
		
		return output;
	}
	
	// 성별별 필터
	@RequestMapping(value="/gender/{id}/matches",method=RequestMethod.GET)
	public List<Matches> getGenderMatches(
			@PathVariable("id") String id) {
		
		List<Matches> output = sqlSession.selectList("MatchesMapper.selectGenderMatches", id);
		
		return output;
	}
	
	// 지역별 필터
	@RequestMapping(value="/region/{id}/matches",method=RequestMethod.GET)
	public List<Matches> getRegionMatches(
			@PathVariable("id") String id) {
		
		List<Matches> output = sqlSession.selectList("MatchesMapper.selectRegionMatches", id);
		
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

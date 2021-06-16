package com.newdeal.footballMaster.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newdeal.footballMaster.model.MatchFilter;
import com.newdeal.footballMaster.service.MatchFilterService;


@RestController
public class MatchController {
	
	@Autowired
	MatchFilterService matchFilterService;
	
	@RequestMapping(value="/matches",method=RequestMethod.GET)
	public List<MatchFilter> getMatches(
			@RequestParam(value="params", required = false) String[] params,
			@RequestParam(value="area") String area,
			@RequestParam(value="day") String day) {
		
		MatchFilter input = new MatchFilter();
		
		String genderRule = "";
		String level = "";
		
		// 성별 파라미터값 분별후 쿼리문으로 전송
		// Male, Female, Mix
		for (int i = 0 ; i < params.length ; i++) {
			if (params[i].equals("Male")) {
				genderRule = "('M')";
				input.setGender_rule(genderRule);
			} else if (params[i].equals("Female")) {
				if (genderRule.equals("('M')")) {
					genderRule = "('M','F')";
					input.setGender_rule(genderRule);
				} else {
					genderRule = "('F')";
					input.setGender_rule(genderRule);
				}
			} else if (params[i].equals("Mix")) {
				if (genderRule.equals("('M')")) {
					genderRule = "('M','H')";
					input.setGender_rule(genderRule);
				} else if (genderRule.equals("('F')")){
					genderRule = "('F','H')";
					input.setGender_rule(genderRule);
				} else if (genderRule.equals("('M','F')")) {
					genderRule = "('M','F','H')";
					input.setGender_rule(genderRule);
				}
			} else {
				genderRule = "('M','F','H')";
				input.setGender_rule(genderRule);
			}
		}
		
		// 레벨 파라미터값 분별후 쿼리문으로 전송
		// Low, Middle, High
		for (int i = 0 ; i < params.length ; i++) {
			if (params[i].equals("Low")) {
				level = "('L')";
				input.setLevel(level);
			} else if (params[i].equals("Middle")) {
				if (level.equals("('L')")) {
					level = "('L','M')";
					input.setLevel(level);
				} else {
					level = "('M')";
					input.setLevel(level);
				}
			} else if (params[i].equals("High")) {
				if (level.equals("('L')")) {
					level = "('L','H')";
					input.setLevel(level);
				} else if (level.equals("('M')")){
					level = "('M','H')";
					input.setLevel(level);
				} else if (level.equals("('L','M')")) {
					level = "('L','M','H')";
					input.setLevel(level);
				}
			} else {
				level = "('L','M','H')";
				input.setLevel(level);
			}
		}
		
		String setArea = "";
		if (area.equals("서울")) {
			setArea = "A";
		} else if (area.equals("경기")) {
			setArea = "B";
		} else if (area.equals("인천")) {
			setArea = "C";
		} else if (area.equals("대전")) {
			setArea = "D";
		} else if (area.equals("대구")) {
			setArea = "E";
		} else if (area.equals("부산")) {
			setArea = "F";
		} else if (area.equals("울산")) {
			setArea = "G";
		} else if (area.equals("광주")) {
			setArea = "H";
		} else if (area.equals("충북")) {
			setArea = "I";
		} else if (area.equals("경북")) {
			setArea = "J";
		} else if (area.equals("전북")) {
			setArea = "K";
		} else if (area.equals("충남")) {
			setArea = "L";
		} else if (area.equals("경남")) {
			setArea = "M";
		}
		
		input.setArea(setArea);

		
		// day 값은 20210507 << 이런식으로 가져와야함 int값 째로 가져올거면 상위 리퀘스트 파람의 데이터 타입을 변경해야함
		// 해당 값을 int로 받아오면 파싱하는 과정 필요 x
		int setDay = Integer.parseInt(day);
		
		input.setDay(setDay);

		
		List<MatchFilter> output = matchFilterService.getMatchFilter(input);
		
		return output;
	}

}

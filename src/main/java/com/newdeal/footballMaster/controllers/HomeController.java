package com.newdeal.footballMaster.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newdeal.footballMaster.model.MatchFilter;
//import com.newdeal.footballMaster.model.User;
import com.newdeal.footballMaster.service.MatchFilterService;
import com.newdeal.footballMaster.service.UserService;

//import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the application home page.
 */
//@Slf4j
@Controller
public class HomeController {
	
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	UserService userService;
	
	@Autowired
	MatchFilterService matchFilterService;
	
	@Value("#{servletContext.contextPath}")
	String contextPath;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = {"/","home"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String home(Locale locale, Model model, HttpServletResponse response, HttpSession session) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
//		// TODO 로그인 끝나고 유저정보에 핸드폰 정보가 존재하지 않으면 정보입력 페이지로 계속 이동시켜버림
		// TODO 해당 코드는 
		// 로그인이 필요한 모든 뷰페이지 컨트롤러에 넣을것임.
//		String accessToken = (String)session.getAttribute("accessToken");
//		if ( accessToken == null ) {
//			try {
//				response.sendRedirect(contextPath + "/home");
//				return "home";
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		
//		
//		// TODO 세션에 저장된 엑세스토큰의 유효성 검사
//		// 코드를 수정해서 값을 리턴받아서 얼럿창을 띄워주는것도 좋아보임
//		// 일단 실패했을때 메인 페이지로 돌아가게 하고 세션값을 초기화 시켜서 재 로그인 시키게 하였음.
//		// 성공하면 그냥 통과
//		String accessToken = (String)session.getAttribute("accessToken");
//		if (userService.checkToken(accessToken)) {
//			try {
//				session.invalidate();
//				response.sendRedirect(contextPath + "/home");
//				return "home";
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		
		
		
		return "home";
	}
	
	// 로그인
	@ResponseBody
	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public int loginUser(HttpSession session,
			@RequestParam(value="email") String email) {
		
		// 받은 이메일값이 없으면 소셜 로그인 자체를 실패한것임.
		// 해당 리턴값을 받고 프론트에서 소셜로그인 실패 얼럿창을 띄워주고 메인페이지로 보내주는게 좋아보임
		if (email == null) {
			return 1;
		} else if ( sqlSession.selectOne("UserMapper.selectUser", email) == null ) {
			// 로그인에는 성공했지만 회원정보가 없을때 리턴값임.
			// 프론트에서 회원가입쪽으로 유도해야함
			return 2;
		}
		
		// 로그인에 성공한값임. 메인페이지로 이동시키거나 해당 페이지 refresh
		return 0;
		
	}
	
	// 로그아웃
	// 세션을 제거하는것이므로 소셜 로그아웃은 프론트에서 해야함(소셜 로그아웃 시키면서 호출하면 좋음)
	@RequestMapping(value="logout.do",method=RequestMethod.GET)
	public void logout(HttpSession session, HttpServletResponse response) { 
		session.invalidate();
		try {
			response.sendRedirect(contextPath + "/home");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*매치필터 ajax*/
	@ResponseBody
	@RequestMapping(value="matchFilter.do", method=RequestMethod.POST)
	public int matchFilter(Model result, HttpServletResponse response, HttpSession session,
			@RequestParam(value="params") String[] params,
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
		
		// 해당 서치값이 없을때
		if (output == null) {
			return 0;
		}
		
		int count = output.size();
		
		result.addAttribute("output", output);
		result.addAttribute("count", count); // 서치된 item 개수
		
		// 해당 리스트 값을 뿌려준다. (가져다 쓰세요)
		return 1;
	}
	
}

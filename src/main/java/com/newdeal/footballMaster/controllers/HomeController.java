package com.newdeal.footballMaster.controllers;

import java.text.DateFormat;
import java.util.Date;
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

//import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the application home page.
 */
//@Slf4j
@Controller
public class HomeController {
	
	@Autowired
	SqlSession sqlSession;
	
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
		
		return "home";
	}
	
	@RequestMapping(value = {"/naver_callback"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String naverCallback(Locale locale, Model model, HttpServletResponse response, HttpSession session) {

		
		return "/naver_callback";
	}
}

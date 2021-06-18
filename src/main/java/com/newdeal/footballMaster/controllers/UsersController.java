package com.newdeal.footballMaster.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.newdeal.footballMaster.model.Matches;
import com.newdeal.footballMaster.model.Response;
import com.newdeal.footballMaster.model.Users;
import com.newdeal.footballMaster.model.UsersBanks;
import com.newdeal.footballMaster.model.UsersCash;
import com.newdeal.footballMaster.service.UsersService;

@RestController
public class UsersController {
	
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	UsersService usersService;
	
	// 단일 유저 정보 호출
	@RequestMapping(value="/my" , method = RequestMethod.GET)
	public Users getUser(
			@RequestHeader("accessToken") String accessToken) {
		
		String email = usersService.checkToken(accessToken);
		
		if (email == null) {	// 토큰이 유효하지 않을때
			return null;
		} else {
			
			Users output = sqlSession.selectOne("UsersMapper.selectUser", email);
			
			return output;
			
		}
	}
	
	// 유저 정보 변경
	@RequestMapping(value="/users" , method = RequestMethod.PUT)
	public Response updateUser(
			@RequestHeader("accessToken") String accessToken,
			@RequestBody final Users user) {
		
		Response answer = new Response();
		
		String email = usersService.checkToken(accessToken);
		
		if (email == null) {
			answer.setResponse("Fail : 토큰이 유효하지 않습니다.");
			return answer;
		} else {
			
			Users input = sqlSession.selectOne("UsersMapper.selectUser", email);
			
			if (input.getGender().equals("남성")) {
				input.setGender("M");
			} else {
				input.setGender("F");
			}
			
			if (user.getGender() != null) {
				if (user.getGender().equals("여성")) {
					input.setGender("F");
				} else {
					input.setGender("M");
				}

			}
		
			if (user.getBirthday() != null) {
				input.setBirthday(user.getBirthday());
			}
			
			if (user.getName() != null) {
				input.setName(user.getName());
			}
			
			if (user.getPhone_number() != 0) {
				input.setPhone_number(user.getPhone_number());
			}
			//TODO 오우 아니야 그냥 계좌 정보도 여기서 수정하게 하셈! 걍 정보 받아서 넣어주는거임! 생성도 받은정보로 생성떄리면댐!
			//TODO 그렇게해서 부르는거임 환불때 계좌정보 입력 시키는것도! 이걸!! 하 하
			//TODO 어짜피 보유 금액보다 적으면 환불 못하니까 환불 불러도 작동 안함 뿌뿌~~
			if (sqlSession.selectOne("UsersBanksMapper.selectUserBank", input.getId()) == null) {
				// 유저의 환불계좌 정보가 없을시 생성
				sqlSession.insert("UsersBanksMapper.createUserBank", input.getId());
			}
			
			sqlSession.update("UsersMapper.updateUser", input);
			
			answer.setResponse("Success : 성공적으로 변경되었습니다.");
			return answer;
		}
	}
	
	// 유저 삭제
	@RequestMapping(value="/users", method = RequestMethod.DELETE)
	public Response deleteUser(
			@RequestHeader("accessToken") String accessToken) {
		
		Response answer = new Response();
		
		String email = usersService.checkToken(accessToken);
		
		if (email == null) {	// 토큰이 유효하지 않을때
			answer.setResponse("Fail : 토큰이 유효하지 않습니다.");
			return answer;
		} else {
			
			Users user = sqlSession.selectOne("UsersMapper.selectUser", email);
			
			if (user == null) {	
				answer.setResponse("Fail : 존재하지 않는 유저입니다.");
				return answer;
			}
			
			int users_id = user.getId();
			
			// TODO 온 디스케이디드 머가댔든 연결해서 지워지게 해줘
			// TODO 두개기능이 합쳐지는 같은경우는 _로 설명을 하던지 동사로..
			sqlSession.delete("UsersMapper.deleteUserBank", users_id);
			sqlSession.delete("UsersMapper.deleteUserMatches", users_id);
			sqlSession.delete("UsersMapper.deleteUserPaymentHistories", users_id);
			sqlSession.delete("UsersMapper.deleteUser", users_id);
			
			answer.setResponse("Success : 성공적으로 삭제되었습니다.");
			return answer;
			
		}
	}
	
	// 유저 생성 및 로그인 ( 소셜로그인이 완료된 뒤에 작동 )
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public Response login(
			HttpSession session,
			@RequestBody final Users user) {
		
		Response answer = new Response();
		
		String email = user.getEmail();
		
		if (email == null) {
			answer.setResponse("Fail : 이메일이 유효하지 않습니다.");
			return answer;
		} else if (sqlSession.selectOne("UsersMapper.selectUser", email) != null){
			usersService.creatToken(session, email);
			answer.setResponse("Success : 로그인 되었습니다.");
			return answer;
		} else {
			sqlSession.insert("UsersMapper.createUser", email);
			usersService.creatToken(session, email);
			answer.setResponse("Success : 회원가입 및 로그인 되었습니다.");
			return answer;
		}
			
	}
	
	// 로그아웃
	// 세션을 제거하는것이므로 소셜 로그아웃은 프론트에서 해야함(소셜 로그아웃 시키면서 호출하면 좋음)
	// 굳이 소셜 로그아웃을 안하고 로그인 버튼 재 선택시 그냥 재 로그인 되게 하면 되긴함.. << 하지만 이렇게하면 호출 타이밍이.. 공부하자
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public Response logout(HttpSession session) { 
		
		Response answer = new Response();
		
		if (session.getAttribute("accessToken") == null) {
			answer.setResponse("Fail : 로그인 상태가 아닙니다.");
		}
		
		session.setAttribute("accessToken", null);
		session.invalidate();
		
		answer.setResponse("Success : 로그아웃 되었습니다.");
		return answer;
	}
	
	// 유저 캐시 사용내역 호출
	@RequestMapping(value="/my_cash", method = RequestMethod.GET)
	public List<UsersCash> getUserCash(
			@RequestHeader("accessToken") String accessToken) {
		
		String email = usersService.checkToken(accessToken);
		
		if (email == null) {
			return null;
		}
		
		Users user = sqlSession.selectOne("UsersMapper.selectUser", email);
		
		int users_id = user.getId();
		
		List<UsersCash> output = sqlSession.selectList("UsersCashMapper.selectUserCash", users_id);
		
		return output;
	}
	
	// 유저 캐시 충전
	@RequestMapping(value="/cash_charge", method = RequestMethod.POST)
	public Response userCashCharge(
			@RequestHeader("accessToken") String accessToken,
			@RequestBody final UsersCash userCash) {
		
		Response answer = new Response();
		
		String email = usersService.checkToken(accessToken);
		
		if (email == null) {
			answer.setResponse("Fail : 토큰이 유효하지 않습니다.");
			return answer;
		}
		
		Users user = sqlSession.selectOne("UsersMapper.selectUser", email);
		
		int price = userCash.getPrice();
		
		if (price <= 0) {
			answer.setResponse("Fail : 비 정상적인 금액입니다.");
			return answer;
		}
		
		UsersCash input = new UsersCash();
		
		input.setUsers_id(user.getId());
		input.setBalance(price + user.getBalance());
		input.setPrice(price);
		
		// TODO 트랜젝션?? 그거 해야함
		sqlSession.insert("UsersCashMapper.chargeCash", input);
		sqlSession.update("UsersCashMapper.changeUserBalance", input);
		
		answer.setResponse("Success : 충전 되었습니다.");
		return answer;
	}
	
	// 유저 캐시 환불
	@RequestMapping(value="/cash_refund", method = RequestMethod.POST)
	public Response userCashRefund(
			@RequestHeader("accessToken") String accessToken,
			@RequestBody final UsersCash userCash) {
		
		Response answer = new Response();
		
		String email = usersService.checkToken(accessToken);
		
		if (email == null) {
			answer.setResponse("Fail : 토큰이 유효하지 않습니다.");
			return answer;
		}
		
		Users user = sqlSession.selectOne("UsersMapper.selectUser", email);
		
		int price = userCash.getPrice();
		
		if (price <= 0) {
			answer.setResponse("Fail : 비 정상적인 금액입니다.");
			return answer;
		}
		
		UsersCash input = new UsersCash();
		
		if (user.getBalance() - price < 0) {
			answer.setResponse("Fail : 환불 금액이 보유 금액보다 많습니다.");
			return answer;
		}
		
		input.setUsers_id(user.getId());
		input.setBalance(user.getBalance() - price);
		input.setPrice(price);
		
		//TODO 트랜젝션@!
		sqlSession.insert("UsersCashMapper.refundCash", input);
		sqlSession.update("UsersCashMapper.changeUserBalance", input);
		
		answer.setResponse("Success : 환불 되었습니다.");
		return answer;
	}
	
	// 매치 예약
	@RequestMapping(value="/reservation/{match_id}", method = RequestMethod.POST)
	public Response reservation(
			@RequestHeader("accessToken") String accessToken,
			@PathVariable("match_id") int match_id) {
		
		Response answer = new Response();
		
		String email = usersService.checkToken(accessToken);
		
		if (email == null) {
			answer.setResponse("Fail : 토큰이 유효하지 않습니다.");
			return answer;
		}
		
		Users user = sqlSession.selectOne("UsersMapper.selectUser", email);
		
		Matches matchInput = sqlSession.selectOne("MatchesMapper.selectMatch", match_id);
		
		UsersCash cashInput = new UsersCash();
		
		int participationFee = matchInput.getParticipation_fee();
		
		if (user.getBalance() - participationFee < 0) {
			answer.setResponse("Fail : 보유 금액이 모자랍니다.");
			return answer;
		}
		
		matchInput.setUsers_id(user.getId());
		matchInput.setStatus("A");
		cashInput.setUsers_id(user.getId());
		cashInput.setBalance(user.getBalance() - participationFee);
		cashInput.setPrice(participationFee);
		
		// 취소했다가 다시 예약하려고 할때
		Matches checkMatch = sqlSession.selectOne("MatchesMapper.checkUserMatch", matchInput);
		if (checkMatch != null) {
			sqlSession.update("MatchesMapper.updateUserMatch", matchInput);
			sqlSession.insert("UsersCashMapper.useCash", cashInput);
			sqlSession.update("UsersCashMapper.changeUserBalance", cashInput);
			
			answer.setResponse("Success : 예약 되었습니다.");
			return answer;
		}
		
		//TODO Think 생각해봐라 유저의 update값을 balance만 받아서 해당값 바꿔주고 그대로 바꿔주면 되지 아니한가?
		//TODO 재활용적인 측면에서 굉장히 유용하다
		
		//TODO 트랜젝션 ㅠㅠ 호다닥 공부 ㄱ
		sqlSession.insert("MatchesMapper.createUserMatches", matchInput);
		sqlSession.insert("UsersCashMapper.useCash", cashInput);
		sqlSession.update("UsersCashMapper.changeUserBalance", cashInput);
		
		answer.setResponse("Success : 예약 되었습니다.");
		return answer;
	}
	
	// 매치 예약 취소
	@RequestMapping(value="/cancle/{match_id}", method = RequestMethod.POST)
	public Response cancle(
			@RequestHeader("accessToken") String accessToken,
			@PathVariable("match_id") int match_id) {
		
		Response answer = new Response();
		
		String email = usersService.checkToken(accessToken);
		
		if (email == null) {
			answer.setResponse("Fail : 토큰이 유효하지 않습니다.");
			return answer;
		}
		
		Users user = sqlSession.selectOne("UsersMapper.selectUser", email);
		
		Matches matchInput = sqlSession.selectOne("MatchesMapper.selectMatch", match_id);
		
		UsersCash cashInput = new UsersCash();
		
		int participationFee = matchInput.getParticipation_fee();
		
		matchInput.setUsers_id(user.getId());
		matchInput.setStatus("C");
		cashInput.setUsers_id(user.getId());
		cashInput.setBalance(user.getBalance() + participationFee);
		cashInput.setPrice(participationFee);
		
		//TODO Think 생각해봐라 유저의 update값을 balance만 받아서 해당값 바꿔주고 그대로 바꿔주면 되지 아니한가?
		//TODO 재활용적인 측면에서 굉장히 유용하다
		
		if (sqlSession.selectOne("MatchesMapper.checkUserMatchDate", match_id) == null) {
			answer.setResponse("Fail : 취소기간이 지났습니다.");
			return answer;
		}
		
		//TODO 트랜젝션 ㅠㅠ 호다닥 공부 ㄱ
		sqlSession.update("MatchesMapper.updateUserMatch", matchInput);
		sqlSession.insert("UsersCashMapper.useCash", cashInput);
		sqlSession.update("UsersCashMapper.changeUserBalance", cashInput);
		
		answer.setResponse("Success : 취소 되었습니다.");
		return answer;
	}
	
	// 유저 환불 계좌 정보 호출
	@RequestMapping(value="/my_bank" , method = RequestMethod.GET)
	public UsersBanks getUserBank(
			@RequestHeader("accessToken") String accessToken) {
		
		String email = usersService.checkToken(accessToken);
		
		if (email == null) {	// 토큰이 유효하지 않을때
			return null;
		} else {
			
			Users user = sqlSession.selectOne("UsersMapper.selectUser", email);
			
			UsersBanks output = sqlSession.selectOne("UsersBanksMapper.selectUserBank", user.getId());
			
			return output;
		}
	}
	
	// 유저 환불 계좌 정보 변경
	@RequestMapping(value="/my_bank" , method = RequestMethod.PUT)
	public Response updateUserBank(
			@RequestHeader("accessToken") String accessToken,
			@RequestBody final UsersBanks userBank) {
		
		Response answer = new Response();
		
		String email = usersService.checkToken(accessToken);
		
		if (email == null) {
			answer.setResponse("Fail : 토큰이 유효하지 않습니다.");
			return answer;
		} else {
			
		Users user = sqlSession.selectOne("UsersMapper.selectUser", email);
			
		UsersBanks input = sqlSession.selectOne("UsersBanksMapper.selectUserBank", user.getId());
		
		if (userBank.getAccount_holder() != null) {
			input.setAccount_holder(userBank.getAccount_holder());
		}
		
		if (userBank.getAccount_number() != null) {
			input.setAccount_number(userBank.getAccount_number());
		}

		int minBankCount = 1;
		int maxBankCount = sqlSession.selectOne("UsersBanksMapper.banksCount");
		if (minBankCount <= userBank.getBanks_id() && userBank.getBanks_id() <= maxBankCount) {
			// front에서 유저 뱅크 json 받을때 DB 참고해서 프론트에서 값 변경해서 가져오기
			// ex) "국민 은행" = 1
			// 사실 백에서 1,2,3,4 이런식으로 바꿔줘도 되긴함.. 하지만 가져올때부터 1,2,3,4 로 하는식이 작업량이 훨씬 줄어듬
			input.setBanks_id(userBank.getBanks_id()); 
		}
		
		//TODO 각 은행별 계좌번호 유효성검사를 하고싶은데 잘 모르겠음 ㅠㅠ 안나오기도하고 나오면 유료라고함..
		
		sqlSession.update("UsersBanksMapper.updateUserBank", input);
			
		answer.setResponse("Success : 정보가 변경되었습니다.");
		return answer;
		}
	}
	
	//TODO 프론트에서 status 상태로 버튼 활성화 비활성화 해야됨
	// 유저 매치 정보 호출
	@RequestMapping(value="/my_matches" , method = RequestMethod.GET)
	public List<Matches> getUserMatch(
			@RequestHeader("accessToken") String accessToken) {
		
		String email = usersService.checkToken(accessToken);
		
		if (email == null) {	// 토큰이 유효하지 않을때
			return null;
		} else {
			
			Users user = sqlSession.selectOne("UsersMapper.selectUser", email);
			
			List<Matches> output = sqlSession.selectList("MatchesMapper.selectUserMatches", user.getId());
			
			return output;
		}
	}
	

}

package com.newdeal.footballMaster.controllers;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.newdeal.footballMaster.jwt.AccessToken;
import com.newdeal.footballMaster.model.Matches;
import com.newdeal.footballMaster.model.Response;
import com.newdeal.footballMaster.model.Users;
import com.newdeal.footballMaster.model.UsersBanks;
import com.newdeal.footballMaster.model.UsersCash;
import com.newdeal.footballMaster.service.UsersCashService;

@RestController
public class UsersController {
	
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	UsersCashService usersCashService;
	
	//TODO 로그를 남겨라
	// 단일 유저 정보 호출
	@RequestMapping(value="/my", method = RequestMethod.GET)
	public Users getUser(
			@RequestHeader(value="accessToken", required=false) String accessToken) {
		
		String email = AccessToken.getInstance().checkToken(accessToken);
		
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
			@RequestHeader(value="accessToken", required=false) String accessToken,
			@RequestBody final Users user) {
		
		Response answer = new Response();
		
		String email = AccessToken.getInstance().checkToken(accessToken);
		
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
			
			if (user.getPhone_number() != null) {
				input.setPhone_number(user.getPhone_number());
			}
			
			sqlSession.update("UsersMapper.updateUser", input);
			
			answer.setResponse("Success : 성공적으로 변경되었습니다.");
			return answer;
		}
	}
	
	// 유저 삭제
	@RequestMapping(value="/users", method = RequestMethod.DELETE)
	public Response deleteUser(
			@RequestHeader(value="accessToken", required=false) String accessToken) {
		
		Response answer = new Response();
		
		String email = AccessToken.getInstance().checkToken(accessToken);
		
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
			
			// 디스케이디드에 대한 공부 필요,,,, 결제 내역은 가지고있어야 된다고 생각해서 안지웠음
			sqlSession.delete("UsersMapper.deleteUserBank", users_id);
			sqlSession.delete("UsersMapper.deleteUserMatches", users_id);
			sqlSession.delete("UsersMapper.deleteUser", users_id);
			
			answer.setResponse("Success : 성공적으로 삭제되었습니다.");
			return answer;
			
		}
	}
	
	// 유저 생성 및 로그인 ( 소셜로그인이 완료된 뒤에 작동 )
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public Response login(
			@RequestBody final Users user) {

		Response answer = new Response();
		
		String email = user.getEmail();
		
		if (email == null) {
			answer.setResponse("Fail : 이메일이 유효하지 않습니다.");
			return answer;
		} else if (sqlSession.selectOne("UsersMapper.selectUser", email) != null){
			answer.setAccessToken(AccessToken.getInstance().creatToken(email));
			answer.setResponse("Success : 토큰이 발급되었습니다.");
			return answer;
		} else {
			sqlSession.insert("UsersMapper.createUser", email);
			Users input = sqlSession.selectOne("UsersMapper.selectUser", email);
			sqlSession.insert("UsersBanksMapper.createUserBank", input.getId());
			answer.setAccessToken(AccessToken.getInstance().creatToken(email));
			answer.setResponse("Success : 회원가입 및 토큰이 발급되었습니다.");
			return answer;
		}
			
	}
	
	// 로그아웃
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public Response logout() { 
		
		Response answer = new Response();

		//TODO 할것이 없더라도 로그를 남기기
		
		answer.setResponse("Success : 로그아웃 되었습니다.");
		return answer;
	}
	
	// 유저 캐시 사용내역 호출
	@RequestMapping(value="/my_cash", method = RequestMethod.GET)
	public List<UsersCash> getUserCash(
			@RequestHeader(value="accessToken", required=false) String accessToken) {
		
		String email = AccessToken.getInstance().checkToken(accessToken);
		
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
			@RequestHeader(value="accessToken", required=false) String accessToken,
			@RequestBody final UsersCash userCash) {
		
		Response answer = new Response();
		
		String email = AccessToken.getInstance().checkToken(accessToken);
		
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
		
		answer.setResponse(usersCashService.chargeCash(input));

		return answer;
	}
	
	// 유저 캐시 환불
	@RequestMapping(value="/cash_refund", method = RequestMethod.POST)
	public Response userCashRefund(
			@RequestHeader(value="accessToken", required=false) String accessToken,
			@RequestBody final UsersCash userCash) {
		
		Response answer = new Response();
		
		String email = AccessToken.getInstance().checkToken(accessToken);
		
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
		
		answer.setResponse(usersCashService.refundCash(input));

		return answer;
	}
	
	// 매치 예약
	@RequestMapping(value="/reservation/{match_id}", method = RequestMethod.POST)
	public Response reservation(
			@RequestHeader("accessToken") String accessToken,
			@PathVariable("match_id") int match_id) {
		
		Response answer = new Response();
		
		String email = AccessToken.getInstance().checkToken(accessToken);
		
		if (email == null) {
			answer.setResponse("Fail : 토큰이 유효하지 않습니다.");
			return answer;
		}
		
		Users user = sqlSession.selectOne("UsersMapper.selectUser", email);
		
		Matches matchInput = sqlSession.selectOne("MatchesMapper.selectMatch", match_id);
		
		if (matchInput == null) {
			answer.setResponse("Fail : 매치가 존재하지 않습니다.");
			return answer;
		}
		
		if (matchInput.getMax_people() <= matchInput.getApplicantCount()) {
			answer.setResponse("Fail : 이미 예약인원이 가득 찼습니다.");
			return answer;
		}
			
		if (sqlSession.selectOne("MatchesMapper.checkMatchDate", match_id) == null) {
			answer.setResponse("Fail : 예약 가능한 기간이 아닙니다.");
			return answer;
		}
		
		UsersCash cashInput = new UsersCash();
		
		int participationFee = matchInput.getParticipation_fee();
		
		if (user.getBalance() - participationFee < 0) {
			answer.setResponse("Fail : 보유 금액이 모자랍니다.");
			return answer;
		}
		
		matchInput.setUsers_id(user.getId());
		cashInput.setUsers_id(user.getId());
		cashInput.setBalance(user.getBalance() - participationFee);
		cashInput.setPrice(participationFee);
		
		// 예약 서비스
		answer.setResponse(usersCashService.reservation(cashInput, matchInput));
		return answer;
	}
	
	// 매치 예약 취소
	@RequestMapping(value="/cancle/{match_id}", method = RequestMethod.POST)
	public Response cancleMatch(
			@RequestHeader(value="accessToken", required=false) String accessToken,
			@PathVariable("match_id") int match_id) {
		
		Response answer = new Response();
		
		String email = AccessToken.getInstance().checkToken(accessToken);
		
		if (email == null) {
			answer.setResponse("Fail : 토큰이 유효하지 않습니다.");
			return answer;
		}
		
		if (sqlSession.selectOne("MatchesMapper.checkMatchDate", match_id) == null) {
			answer.setResponse("Fail : 취소기간이 지났습니다.");
			return answer;
		}
		
		Users user = sqlSession.selectOne("UsersMapper.selectUser", email);
		
		Matches matchInput = sqlSession.selectOne("MatchesMapper.selectMatch", match_id);
		
		UsersCash cashInput = new UsersCash();
		
		int participationFee = matchInput.getParticipation_fee();
		
		matchInput.setUsers_id(user.getId());
		cashInput.setUsers_id(user.getId());
		cashInput.setBalance(user.getBalance() + participationFee);
		cashInput.setPrice(participationFee);
		
		// 예약 취소 서비스
		answer.setResponse(usersCashService.reservation(cashInput, matchInput));
		return answer;
	}
	
	// 유저 환불 계좌 정보 호출
	@RequestMapping(value="/my_bank" , method = RequestMethod.GET)
	public UsersBanks getUserBank(
			@RequestHeader(value="accessToken", required=false) String accessToken) {
		
		String email = AccessToken.getInstance().checkToken(accessToken);
		
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
			@RequestHeader(value="accessToken", required=false) String accessToken,
			@RequestBody final UsersBanks userBank) {
		
		Response answer = new Response();
		
		String email = AccessToken.getInstance().checkToken(accessToken);
		
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
	
	//TODO 프론트에서 status 상태로 버튼 활성화 비활성화 해야됨 status 값은 가지고오니까 프론트에서 ㄱ
	// 취소때릴때 기간 지난것도 취소신청 비활성화 시키려면 무언가 조치를 취해야함. 일단 막아놓긴했음 버튼적인 의미에서임 << 안해도 무방함!
	// 유저 매치 정보 호출
	@RequestMapping(value="/my_matches" , method = RequestMethod.GET)
	public List<Matches> getUserMatches(
			@RequestHeader(value="accessToken", required=false) String accessToken) {
		
		String email = AccessToken.getInstance().checkToken(accessToken);
		
		if (email == null) {	// 토큰이 유효하지 않을때
			return null;
		} else {
			
			Users user = sqlSession.selectOne("UsersMapper.selectUser", email);
			
			List<Matches> output = sqlSession.selectList("MatchesMapper.selectUserMatches", user.getId());
			
			return output;
		}
	}
	

}

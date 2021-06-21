package com.newdeal.footballMaster.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newdeal.footballMaster.model.Matches;
import com.newdeal.footballMaster.model.UsersCash;
import com.newdeal.footballMaster.service.UsersCashService;

@Service
public class UsersCashServiceImpl implements UsersCashService{
	
	@Autowired
	SqlSession sqlSession;

	@Transactional
	@Override
	public String chargeCash(UsersCash input){
		
		String output;
		
		try {
			sqlSession.insert("UsersCashMapper.chargeCash", input); // 충전 내역 추가
			sqlSession.update("UsersCashMapper.changeUserBalance", input); // 실제 금액 조정
			output = "Success : 충전 되었습니다.";
			return output;
		} catch (Exception e){
			output = "Fail : 캐시 충전 트랜잭션 처리중 에러 발생";
			return output;
		}
	}

	@Transactional
	@Override
	public String refundCash(UsersCash input) {
		
		String output;
		
		try {
			sqlSession.insert("UsersCashMapper.refundCash", input); // 충전 내역 추가
			sqlSession.update("UsersCashMapper.changeUserBalance", input); // 실제 금액 조정
			output = "Success : 환불 되었습니다.";
			return output;
		} catch (Exception e){
			output = "Fail : 캐시 환불 트랜잭션 처리중 에러 발생";
			return output;
		}
	}
	
	@Transactional
	@Override
	public String reservation(UsersCash cashInput, Matches matchInput){
		
		String output;
		
		// 취소했다가 다시 예약하려고 할때
		String userMatchStatus = sqlSession.selectOne("MatchesMapper.checkUserMatchStatus", matchInput);
		
		if (userMatchStatus.equals("A")) {
			output = "Fail : 이미 예약된 상태입니다.";
			return output;
		}
		
		matchInput.setStatus("A");
		
		try {
			
			if (userMatchStatus.equals("C")) {
				sqlSession.update("MatchesMapper.updateUserMatch", matchInput);
				sqlSession.insert("UsersCashMapper.useCash", cashInput);
				sqlSession.update("UsersCashMapper.changeUserBalance", cashInput);
						
				output = "Success : 예약 되었습니다.";
				return output;
			}
			
			sqlSession.insert("MatchesMapper.createUserMatches", matchInput);
			sqlSession.insert("UsersCashMapper.useCash", cashInput);
			sqlSession.update("UsersCashMapper.changeUserBalance", cashInput);
			
			output = "Success : 예약 되었습니다.";
			return output;
		
		} catch (Exception e){
			output = "Fail : 매치 예약 트랜잭션 처리중 에러 발생";
			return output;
		}
		
	}
	
	@Transactional
	@Override
	public String cancleMatch(UsersCash cashInput, Matches matchInput){
		
		String output;
		
		String userMatchStatus = sqlSession.selectOne("MatchesMapper.checkUserMatchStatus", matchInput);
		
		if (userMatchStatus.equals("C")) {
			output = "Fail : 이미 취소된 상태입니다.";
			return output;
		}
		
		matchInput.setStatus("C");
		
		try {
			
			sqlSession.update("MatchesMapper.updateUserMatch", matchInput);
			sqlSession.insert("UsersCashMapper.withdrawalCash", cashInput);
			sqlSession.update("UsersCashMapper.changeUserBalance", cashInput);
			
			output = "Success : 취소 되었습니다.";
			return output;
		
		} catch (Exception e){
			output = "Fail : 매치 예약 취소 트랜잭션 처리중 에러 발생";
			return output;
		}
		
	}

}

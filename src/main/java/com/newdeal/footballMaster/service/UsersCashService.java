package com.newdeal.footballMaster.service;

import com.newdeal.footballMaster.model.Matches;
import com.newdeal.footballMaster.model.UsersCash;

public interface UsersCashService {
	
	// 유저 캐시 충전
	public String chargeCash(UsersCash input);
	
	// 유저 캐시 환불
	public String refundCash(UsersCash input);
	
	// 유저 매치 예약
	public String reservation(UsersCash cashInput, Matches matchInput);
	
	// 유저 매치 취소
	public String cancelMatch(UsersCash cashInput, Matches matchInput);

}

package com.newdeal.footballMaster.model;

import lombok.Data;

@Data
public class UsersCash {
	private int id;					// 유저의 거래내역 프라이머리 키
	private String type;			// 유저의 거래내역 타입 (환불, 충전, 사용)
	private int price;				// 금액
	private String created_at;		// 거래 날짜
	private int users_id;			// 유저 프라이머리 키
	private int balance;			// 유저의 남은 잔액
}

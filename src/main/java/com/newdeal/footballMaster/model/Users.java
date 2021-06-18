package com.newdeal.footballMaster.model;

import lombok.Data;

@Data
public class Users {
	private int id;					// 유저 프라이머리 키
	private String email;			// 유저 이메일
	private String name;			// 유저의 이름
	private String gender;			// 유저의 성별
	private String birthday;		// 유저의 생일
	private String phone_number;	// 유저의 핸드폰 번호
	private int balance;			// 유저의 남은 잔액
	private int fair_point;			// 유저의 페어포인트 점수
}


package com.newdeal.footballMaster.model;

import lombok.Data;

@Data
public class UsersBanks {
	private int id;					// 유저의 은행정보 프라이머리 키
	private String account_number;	// 유저의 계좌번호
	private String account_holder;	// 예금주 명
	private int banks_id;			// 은행 프라이머리 키
	private int users_id;			// 유저 프라이머리 키
	private String name;			// 은행 이름
}

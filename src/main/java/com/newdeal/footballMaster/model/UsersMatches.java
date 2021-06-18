package com.newdeal.footballMaster.model;

import lombok.Data;

@Data
public class UsersMatches {
	private int id;				// 유저의 매치 프라이머리 키
	private int matches_id;		// 매치 프라이머리 키
	private int users_id;		// 유저 프라이머리 키
}

package com.newdeal.footballMaster.model;

import lombok.Data;

@Data
public class Matches {
	private int id;						// 매치 프라이머리 키
	private String match_date;			// 매치 날짜
	private String gender_rule;			// 성별 룰
	private String level;				// 선수 체급
	private int fields_id;				// 필드 프라이머리 키
	private int managers_id;			// 매니저 프라이머리 키
	private int users_id;				// 유저 프라이머리 키
	private String status;				// 유저의 매치 취소 상태
	private String name;				// 구장 이름
	private String place;				// 구장 장소
	private int participation_fee;		// 참가비
	private String man_to_man_rule;		// 인원수 룰
	private String floor_material;		// 바닥 재질
	private String shose_rule;			// 신발 룰
	private int min_people;				// 최소 인원
	private int max_people;				// 최대 인원
	private String size;				// 구장 크기
	private String shower_room;			// 샤워장 유무
	private String park;				// 주차장 유무
	private String shose_rent;			// 신발 대여 가능 여부
	private String clothes_rent;		// 운동복 대여 가능 여부
	private String special_thing;		// 특이사항
	private String area;				// 구장 지역
	private String manager_name;		// 매니저 이름
	private String phone_number;			// 매니저 핸드폰 번호
	private String birthday;			// 매니저 생일
	private String greetings;			// 한마디
}

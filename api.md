
# 메인화면에서 소셜매칭리스트 보여주기
```
GET /matches
```
<br>

### RequestParams
<br>

Name 		|Type| Description
---- 		|----| ---- 
match_date	|date| 매치날짜를 조회합니다.
gender_rule |enum| 매치리스트에 성별 필터를 적용합니다.
level		|enum| 매치 수준? 난이도 필터를 적용합니다.
region		|enum| 매치 지역 필터를 적용합니다.

### Response
<br>

```
{
	"id" : "1",
  	"match_date" : "20210623",
  	"gender_rule" : "M",
  	"level" : "H",
 	"region" : "A"
}
```

# 필터적용
```
GET /$filter/{id}/matches
```
### RequestParams
<br>

Name | Type | Description
---- | ---- | ---- 
level | enum | 매치 수준을 적용합니다|
gender| enum | 매치 성별을 적용합니다
region| enum | 매치 지역을 적용합니다
id | int | 해당 id의 매치를 조회합니다

### Response
<br>

```
[
{
  "gender" : "M",
  "level" : "H",
  "region" : "A",
  "id" : 1
},
{
  "gender" : "M",
  "level" : "H",
  "region" : "A",
  "id" : 4
}
{
  "gender" : "M",
  "level" : "H",
  "region" : "A",
  "id" : 8
}
]

```

# 원하는 소셜매칭리스트 조회하기
```
GET /matches/{id}
```
### RequestParams
<br>

Name 		|Type| Description
---- 		|----| ---- 
id	|int| 해당 id의 소셜매칭정보를 조회합니다

###Response
<br>

```
{
	"id" : "1",
  	"match_date" : "20210623",
  	"gender_rule" : "F",
  	"level" : "L",
 	"region" : "B",
 	"name" : "용산구장",
 	"participation_fee" : "10,000원",
 	"location" : "서울시 강남구 논현동 00-0번지"
 	"man_to_man_rule" : "5 vs 5"
 	"floor_material" : "인조잔디"
 	"shoes_rule" : "풋살화 착용 필수"
 	"min_people" : "6인"
 	"max_people" : "12인"
 	"size" : "200 x 200 (m)"
 	"shower_room" : "샤워룸 있음"
 	"clothes_rent" : "운동복 대여 가능"
 	"special_thing" : "특이사항 없음"
}
```

# 마이페이지 조회하기
```
GET /my
```
### RequestHeader

Name|Type|Description
---- |----| ---- 
accessToken|String|토큰을 통해 해당 유저의 정보를 가져옵니다.
<br>

### RequestParams
<br>

Name 		|Type| Description
---- 		|----| ---- 
email	|String| 유저의 계정 정보를 조회합니다

###Response
<br>

```
{
	"id" : "1",
  	"email" : "footballmaster@gmail.com",
}
```

# 유저정보 변경하기
```
PUT /users
```
### RequestHeader
Name|Type|Description
---- |----| ---- 
accessToken|String|토큰정보를 확인하여 유저 정보를 확인합니다.
<br>

### RequestParams
<br>

Name 		|Type| Description
---- 		|----| ---- 
accessToken |String| 토근을 통해 유저 정보를 가져옵니다.
gender	|enum| 유저의 성별 정보를 변경합니다.
birthday|date| 유저의 생일 정보를 변경합니다.
phone_number|int| 유저의 휴대전화 정보를 변경합니다.

###Response
<br>

```
{
	Success : "성공적으로 변경되었습니다",
	Fail : "정보 변경을 실패하였습니다"
	
}
```
# 유저정보 삭제하기
```
DELETE /users
```

### RequestParams
<br>

Name |Type| Description
---- |----| ---- 
user_id||int|유저 아이디를 조회하여 삭제한다

###Response

<br>

```
{
	Success : "성공적으로 삭제되었습니다"
	Fail : "유저 정보 삭제를 실패하였습니다"
}
```

# 유저 생성 및 로그인
```
POST /login
```
### RequestHeader
<br>

Name| Type| Description
----|----|----
accessToken|String|JWT로 유저 생성 및 로그인을 시도할 때 토큰을 발급합니다.

###Response
<br>

```
{
	Success : "로그인 성공"
	Fail : "로그인 실패"
}
```

# 유저 로그아웃
```
GET /logout
```

### RequestParam
<br>

Name|Type|Description
----|----|----
accessToken|String| 쿠키에서 JWT를 삭제하여 로그아웃한다

###Resposne
<br>

```
{
	Success : "로그아웃 성공"
	Fail : "로그아웃 실패"
}
```

# 유저 캐시충전
```
POST /cash_charge
```

### RequestHeader
Name|Type|Description
---- |----| ---- 
accessToken|String|토큰을 발급해 해당 유저의 정보를 가져옵니다.

### RequestParam
<br>

Name|Type|Description
----|----|----
price|int| 충전할 캐시 금액을 지정한다
user_id|int| 유저 아이디를 조회한다
balance|int| 유저의 남은 캐시잔액을 조회한다
type|String|유저 거래내역 타입(충전, 사용, 환불)
created_at|String| 거래날짜를 확인한다.

###Response
<br>

```
{
	Success : "캐시를 충전하였습니다"
	Fail : "캐시 충전 실패"
}
```

# 캐시 환불
```
POST /cash_refund
```

### RequestHeader
Name|Type|Description
---- |----| ---- 
accessToken|String|토큰을 발급해 해당 유저의 정보를 가져옵니다.

### RequestParam
<br>

Name|Type|Description
----|----|----
price|int| 충전할 캐시 금액을 지정한다
user_id|int| 유저 아이디를 조회한다
balance|int| 유저의 남은 캐시잔액을 조회한다
type|String|유저 거래내역 타입(충전, 사용, 환불)
created_at|String| 거래날짜를 확인한다.

### Response
<br>

```
{
	Success : "환불이 완료되었습니다"
	Fail case1 : "비 정상적인 금액입니다"
	Fail case2 : "환불 금액이 보유 금액보다 많습니다"
	Fail case3 : "환불 실패"
}
```

# 매치예약하기
```
POST /reservation/{match_id}
```

### RequestParam
<br>

Name|Type|Description
----|----|----
id |int| 매치 고유 아이디 정보
match_date|String| 매치 날짜를 생성한다
gender_rule |String| 매치의 성별 규칙을 생성한다
stadiums_id|int| 구장 고유 번호를 등록한다
managers_id|int| 구장 내 매니저의 고유 id 값을 등록한다
user_id|int| 매칭 사용자인 유저의 고유 id 값에 매칭정보를 저장한다
status|String| 사용자의 매칭 상태를 저장한다
name|String| 구장의 이름 정보를 저장한다
location|String| 구장의 실 주소 정보를 저장한다
participation_fee|int| 매칭 참가비 정보를 저장한다
man_to_man_rule|String| 인원 수 규칙을 저장한다
floor_material|String| 구장 바닥의 재질정보를 저장한다
shose_rule|String| 풋살화 착용에 대한 규칙정보를 저장한다
min_people|int| 매칭 참가 최소 인원 수를 저장한다
max_people|int| 매칭 참가 최대 인원 수를 저장한다
size|String| 구장 크기 정보를 저장한다
shower_room|String| 구장 내 샤워장 유무 정보를 저장한다
park|String| 구장 내 전용 주차장 유무 정보를 저장한다
shose_rent|String| 풋살화 대여 여부 정보를 저장한다
clothes_rent|String| 운동복 대여 여부 정보를 저장한다
special_thing|String| 구장의 특이사항 정보를 저장한다
region|String| 구장의 지역정보를 저장한다
manager_name|String| 지정된 매니저의 이름 정보를 저장한다
phone_number|String| 지정된 매니저의 휴대전화 정보를 저장한다
greeting|String| 지정된 매니저의 인사말 정보를 저장한다
applicantCount|int| 해당 매치의 신청자 수를 저장한다
day_of_week|String| 해당 매치 진행 요일 정보를 저장한다

### Response
<br>

```
{
	id : "1",
	match_date : "20210623",
	gender_rule : "H",
	staduims_id : "12345",
	managers_id : "008",
	user_id : "1",
	status : "매칭 진행중",
	name : "선정릉풋살스타디움",
	location : "서울시 강남구 논현동 00-0번지",
	paricipation_fee : "10,000원",
	man_to_man_rule : "5 vs 5",
	floor_material : "인조잔디",
	shose_rule : "풋살화 착용 필수",
	min_people : "6명",
	max_people : "12명",
	size : "200 X 200",
	shower_room : "O",
	park : "O",
	shose_rent : "O",
	clothes_rent : "O",
	special_thing : "특이사항 없음",
	region : "서울",
	manager_name : "최희재",
	phone_number : "010-0000-0000",
	birthday : "19990101",
	greeting : "잘부탁드립니다",
	applicantCount : "4명 / 6명",
	day_of_week : "수요일"
	
	Success : " 매칭 성공 ",
	Fail case1 : " 매치가 존재하지 않습니다 ",
	Fail case2 : " 이미 예약 인원이 가득 찼습니다 ",
	Fail case3 : " 예약 가능 기간이 아닙니다 ",
	Fail case4 : " 보유 금액이 모자랍니다 "
}
```

# 매치 예약 취소하기
```
POST /cancel/{match_id}
```

### RequestHeader

Name|Type|Description
---- |----| ---- 
accessToken|String|토큰값을 비교해 해당 유저가 예약한 매치 정보를 가져옵니다.

### RequestParam
<br>

Name|Type|Description
---- |----| ---- 
match_id|int| 매치 id 값에 해당하는 매치 예약 정보를 삭제합니다.

### Response
<br>

```
{
	Success : "예약 취소 완료"
	Fail : " 예약취소 실패하였습니다"
}
```

# 환불 계좌정보 조회하기
```
GET /my_bank
```

### RequestHeader

Name|Type|Description
---- |----| ---- 
accessToken|String|토큰을 발급해 해당 유저의 정보를 가져옵니다.

### RequestParam

Name|Type|Description
user_id|int| id 값에 해당하는 계좌번호정보를 조회한다
account_number|String| 계좌번호 정보를 조회한다
account_holder|String| 예금주 정보를 조회한다
name |String| 은행의 이름정보를 조회한다

### Response
<br>

```
{
	user_id : "1",
	name : "신한",
	account_number : "11-123456-123",
	account_holder : "최희재"
}
```

# 환불 계좌정보 변경하기
```
PUT /my_bank
```

### RequestHeader

Name|Type|Description
---- |----| ---- 
accessToken|String|토큰을 발급해 해당 유저의 계좌 정보를 가져옵니다.

### RequestParam
<br>

Name|Type|Description
user_id|int| id 값에 해당하는 계좌번호정보를 조회한다
account_number|String| 계좌번호 정보를 변경한다
account_holder|String| 예금주 정보를 변경한다
name |String| 은행의 이름정보를 변경한다

### Response
<br>

```
{
	Success : "계좌정보 변경 완료"
	Fail : "계좌정보 변경 실패"
}
```

# 유저 매칭정보 조회하기
```
GET /my_matches
```

### RequestParams
<br>

Name|Type|Description
---- |----| ---- 
user_id|int| id 값에 해당하는 매칭정보를 조회한다
match_dats|String| 매칭 날짜 정보를 조회한다
participation_fee|int| 매칭에 필요한 참가비를 조회한다
name|String| 매칭된 구장 이름정보를 조회한다

### Reponse
<br>

```
{
	user_id : "1",
	match_dates : "20210623",
	participation_fee : "10,000원",
	name : "용산스타디움"
}
```


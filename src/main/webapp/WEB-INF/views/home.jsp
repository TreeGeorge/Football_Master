<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<html>
<head>
<!-- 
	<meta name="google-signin-scope" content="profile email">
    <meta name="google-signin-client_id" content="852386834583-rqsk6ce3b7d5r3vm0f3fj72211trgh6g.apps.googleusercontent.com">
    <script src="https://apis.google.com/js/platform.js" async defer></script>
 -->
	<title>Home</title>
	<meta charset='utf-8' />
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>

<button type="button" onclick="signOut();">로그아웃</button>
<a href="javascript:kakaoLogin()"><img src="https://www.gb.go.kr/Main/Images/ko/member/certi_kakao_login.png" /></a>
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script src="assets/js/jquery-3.5.1.min.js"></script>



<!-- 네이버아이디로로그인 버튼 노출 영역 -->
<div id="naverIdLogin"></div>

<!-- 네이버 로그인 부분 -->
<script type="text/javascript" src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js" charset="utf-8"></script>
<script type="text/javascript">
  	var naverLogin = new naver.LoginWithNaverId(
  			{
  				clientId: "fVWpyHFN5yLxAUXa4chY",
  				callbackUrl: "http://localhost:8080/footballMaster/naver_callback",
  				isPopup: false, /* 팝업을 통한 연동처리 여부 */
  				loginButton: {color: "green", type: 3, height: 60} /* 로그인 버튼의 타입을 지정 */
  			}
  		);
  		
  	/* 설정정보를 초기화하고 연동을 준비 */
  	naverLogin.init();
</script>



<!-- 구글 로그인 부분... ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ -->
<!-- 
<div class="g-signin2" data-onsuccess="onSuccess" data-theme="dark"></div>
 -->
<script>
	<!-- 쿠키 생성 -->
	function setCookie(name, value, options = {}) {
		  options = {
		    path: 'http://localhost:8080/footballMaster',
		    // 필요한 경우, 옵션 기본값을 설정할 수도 있습니다.
		    ...options
		  };
		  if (options.expires instanceof Date) {
		    options.expires = options.expires.toUTCString();
		  }
		  let updatedCookie = encodeURIComponent(name) + "=" + encodeURIComponent(value);
		  for (let optionKey in options) {
		    updatedCookie += "; " + optionKey;
		    let optionValue = options[optionKey];
		    if (optionValue !== true) {
		      updatedCookie += "=" + optionValue;
		    }
		  }
		  document.cookie = updatedCookie;
	} // end setCookie(name, value, options = {})

</script>






<script>
	//067eac8fe2c1a4d95ccddfdc2ab86007
	window.Kakao.init("067eac8fe2c1a4d95ccddfdc2ab86007");
	
	var temp2;
	function kakaoLogin() {
		
	//	Kakao.API.request({
	//	    url: '/v2/user/scopes',
	//	    data: {
	//	        scopes: ["account_email","gender"]
	//	    },
	//	    success: function(response) {
	//	        console.log(response);
	//	    },
	//	    fail: function(error) {
	//	        console.log(error);
	//	    }
	//	});
		
		window.Kakao.Auth.login({
			scope:'profile, account_email, gender',
			success: function(authObj) {
				console.log(authObj);
				window.Kakao.API.request({
					url:'/v2/user/me',
					success: res => {
						const kakao_account = res.kakao_account;
						console.log(kakao_account);
						console.log(kakao_account.email); // << 유저 이메일값
						temp2 = kakao_account.email;
					}
				});
			}
		});
	}
      
    function signOut() {    // 소셜 로그인 타입을 저장해놓고 해당 타입을 불러와서 if 조건문안에 넣어주기
    	gapi.auth2.getAuthInstance().disconnect();
    	Kakao.Auth.logout();
    	$(location).attr('href','/footballMaster');
    	  
    }
    
    function logIn() {
    	
    }
    

    </script>
</body>
</html>
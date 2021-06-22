<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<html>
<head>
	<meta name="google-signin-scope" content="profile email">
    <meta name="google-signin-client_id" content="852386834583-rqsk6ce3b7d5r3vm0f3fj72211trgh6g.apps.googleusercontent.com">
    <!-- (1) LoginWithNaverId Javscript SDK -->
    <script src="https://apis.google.com/js/platform.js" async defer></script>
	<title>Home</title>
	<meta charset='utf-8' />
	<link rel="stylesheet" href="assets/plugins/sweetalert/sweetalert2.min.css">
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
  <!-- 네이버아이디로로그인 버튼 노출 영역 -->
<div id="naverIdLogin"></div>
<div class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>
<button type="button" onclick="signOut();">로그아웃</button>
<a href="javascript:kakaoLogin()"><img src="https://www.gb.go.kr/Main/Images/ko/member/certi_kakao_login.png" /></a>
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script src="assets/js/jquery-3.5.1.min.js"></script>
<script src="assets/plugins/sweetalert/sweetalert2.all.min.js"></script>

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

	
    function onSignIn(googleUser) {
        // Useful data for your client-side scripts:
        var profile = googleUser.getBasicProfile();
        console.log("ID: " + profile.getId()); // Don't send this directly to your server!
        console.log('Full Name: ' + profile.getName());
        console.log('Given Name: ' + profile.getGivenName());
        console.log('Family Name: ' + profile.getFamilyName());
        console.log("Image URL: " + profile.getImageUrl());
        console.log("Email: " + profile.getEmail());	// << 유저 이메일값

        var email_value = profile.getEmail();
        

    }
      
    function signOut() {    // 소셜 로그인 타입을 저장해놓고 해당 타입을 불러와서 if 조건문안에 넣어주기
    	gapi.auth2.getAuthInstance().disconnect();
    	$(location).attr('href','logout.do');
//    	  if (temp) {
//   		  gapi.auth2.getAuthInstance().disconnect();
//
//
//    	  } else if (temp2) {
//    		  if (!Kakao.Auth.getAccessToken()) {
//    			  console.log('로그인 안대찌롱');
//    			  return;
//    		  }
//    		  
//    		  Kakao.Auth.logout(function() {
//    			  console.log('로그아웃 대찌롱');
//    			  temp2 = null;
//    		  })
//    		  
//    	  }
    	  
    }
    
    function logIn() {
    	
    }
    

    </script>
</body>
</html>
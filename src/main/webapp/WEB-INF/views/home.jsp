<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<html>
<head>
	<meta name="google-signin-scope" content="profile email">
    <meta name="google-signin-client_id" content="852386834583-rqsk6ce3b7d5r3vm0f3fj72211trgh6g.apps.googleusercontent.com">
    <script src="https://apis.google.com/js/platform.js" async defer></script>
	<title>Home</title>
	<meta charset='utf-8' />
	<link rel="stylesheet" href="assets/plugins/sweetalert/sweetalert2.min.css">
	<link rel="stylesheet" href="assets/plugins/ajax/ajax_helper.css">
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>

<div class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>
<button type="button" onclick="signOut();">로그아웃</button>
<a href="javascript:kakaoLogin()"><img src="https://www.gb.go.kr/Main/Images/ko/member/certi_kakao_login.png" /></a>
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script src="assets/js/jquery-3.5.1.min.js"></script>
<script src="assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
<script src="assets/plugins/ajax/ajax_helper.js"></script>
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
        

        $.post('signInUser.do',{email:email_value},function(req){
            if(req == 1){
            	swal({
                    html: "<b>회원가입을 축하드립니다.</b>",    // 내용
                    type: "success",  // 종류
                    confirmButtonText: "확인", // 확인버튼 표시 문구
                    confirmButtonColor: "#ff3253", // 확인버튼 색상
                }).then(function(){
                	$(location).attr('href','/footballMaster');
                });
            }else if (req == 0){
            	swal({
                    html: "<b>이미 가입되어있는 아이디 입니다.</b>",    // 내용
                    type: "error",  // 종류
                    confirmButtonText: "확인", // 확인버튼 표시 문구
                    confirmButtonColor: "#ff3253", // 확인버튼 색상
                }).then(function(){
             //   	$(location).attr('href','/footballMaster');
                });
            }
        });
        // The ID token you need to pass to your backend:
        // var id_token = googleUser.getAuthResponse().id_token;
        // console.log("ID Token: " + id_token);
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
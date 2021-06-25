<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!doctype html>
<html lang="ko">
	<head>
	</head>
	<body>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script type="text/javascript" src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js" charset="utf-8"></script>
	<script type="text/javascript">
		
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
		}
		
		<!-- (2) LoginWithNaverId Javscript 설정 정보 및 초기화 -->
		var naverLogin = new naver.LoginWithNaverId(
			{
				clientId: "fVWpyHFN5yLxAUXa4chY",
				callbackUrl: "http://localhost:8080/footballMaster/naver_callback",
				isPopup: false,
				callbackHandle: true
				/* callback 페이지가 분리되었을 경우에 callback 페이지에서는 callback처리를 해줄수 있도록 설정합니다. */
			}
		);

		/* (3) 네아로 로그인 정보를 초기화하기 위하여 init을 호출 */
		naverLogin.init();

		/* (4) Callback의 처리. 정상적으로 Callback 처리가 완료될 경우 main page로 redirect(또는 Popup close) */
		window.addEventListener('load', function () {
			naverLogin.getLoginStatus(function (status) {
				if (status) {
					/* (5) 필수적으로 받아야하는 프로필 정보가 있다면 callback처리 시점에 체크 */
					var email = naverLogin.user.getEmail();
					if( email == undefined || email == null) {
						alert("이메일은 필수정보입니다. 정보제공을 동의해주세요.");
						/* (5-1) 사용자 정보 재동의를 위하여 다시 네아로 동의페이지로 이동함 */
						naverLogin.reprompt();
						return;
					}
					
					
					var emailJson = {
					        "email" : email
					}
					
					$.ajax({
				        url:'/footballMaster/login'
				        , method : 'POST'
				        , data: JSON.stringify(emailJson)
				        , contentType : 'application/json; charset=UTF-8'
				        , dataType : 'json'
				        , success :function(resp){
				        	setCookie('accessToken', resp['accessToken'], {secure: false, 'max-age': 3600});
				        }, complete : function() {
				        	window.location.replace("http://" + window.location.hostname + 
		                            ( (location.port==""||location.port==undefined)?"":":" + location.port) + "/footballMaster");
				        }
				    });	
				                                                  
                 } else {
					console.log("callback 처리에 실패하였습니다.");
				}
			});
		});
		</script>
	</body>
</html>
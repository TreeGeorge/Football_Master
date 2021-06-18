package com.newdeal.footballMaster.jwt;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AccessToken {
	
	// TMI : 고유 암호화 키인데 하드코딩보다는 외부에서 끌고오는게 좋다..
	final String key = "어림도없지";
	
	private static AccessToken current = null;
	
	public static AccessToken getInstance() {
		if (current == null) {
			current = new AccessToken();
		}
		return current;
	}
	
	public static void freeInstance() {
		// 객체에 null을 대입하면 메모리에서 삭제된다.
		current = null;
	}
	
	private AccessToken() {
		super();
	}
	

    //토큰 생성
    public String createToken(String email) {

        //Header 부분 설정
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        //payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("data", email);

        Long expiredTime = 1000 * 60L * 60L * 2L; // 토큰 유효 시간 (2시간)

        Date ext = new Date(); // 토큰 만료 시간
        ext.setTime(ext.getTime() + expiredTime);
     
        // 토큰 Builder
        String jwt = Jwts.builder()
                .setHeader(headers) // Headers 설정
                .setClaims(payloads) // Claims 설정
                .setSubject("user") // 토큰 용도 
                .setExpiration(ext) // 토큰 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, key.getBytes()) // HS256과 Key로 Sign
                .compact(); // 토큰 생성

        return jwt;
    }
    
    //토큰 검증
    public Map<String, Object> verifyJWT(String jwt) throws UnsupportedEncodingException {
        Map<String, Object> claimMap = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key.getBytes("UTF-8")) // Set Key
                    .parseClaimsJws(jwt) // 파싱 및 검증, 실패 시 에러
                    .getBody();

            claimMap = claims;

            //Date expiration = claims.get("exp", Date.class);
            //String data = claims.get("data", String.class);
            
        } catch (ExpiredJwtException e) { // 토큰이 만료되었을 경우
            System.out.println("만료된 토큰입니다.");
        } catch (Exception e) { // 그외 에러났을 경우
            System.out.println("토큰의 유효성에 어긋납니다");
        }
        return claimMap;
    }    
    
    public String checkToken(String accessToken){
		
		AccessToken checkToken = new AccessToken();
		
		Map<String, Object> result;
		try {
			result = checkToken.verifyJWT(accessToken);
			if (result == null) {
				return null;
			} else {
				String email = (String) result.get("data");
				return email;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
    
    public void creatToken(HttpSession session, String email) {
		
		AccessToken createToken = new AccessToken();
		String accessToken = createToken.createToken(email);
		
		session.setAttribute("accessToken", accessToken);
	}
   
}

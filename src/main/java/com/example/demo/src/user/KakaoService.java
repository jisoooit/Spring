package com.example.demo.src.user;
import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

import java.util.HashMap;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class KakaoService {

    public String getAccessToken(String authorizedCode) {
        System.out.println("getAccessToken 호출");
        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "9adca47b25d38d5f1826188403e6caca");
        params.add("redirect_uri", "http://localhost:9000/oauth/kakao");
        params.add("code", authorizedCode);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // Http 요청하기, Post방식으로, 그리고 response 변수의 응답 받음
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // JSON -> 액세스 토큰 파싱
        String tokenJson = response.getBody();
//        JSONObject rjson = new JSONObject(tokenJson);
//        String accessToken = rjson.getString("access_token");   //우리가 필요한건 accessToken
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(tokenJson);

        String accessToken = element.getAsJsonObject().get("access_token").getAsString();
        String refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

        System.out.println("access_token : " + accessToken);
        System.out.println("refresh_token : " + refreshToken);

        return accessToken;
    }
    public HashMap<String, Object> getUserInfoByToken(String accessToken) {
        HashMap<String, Object> userInfo = new HashMap<>();
        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        // Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(response.getBody());
        System.out.println(element);
        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
        JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

        String nickname = properties.getAsJsonObject().get("nickname").getAsString();
        String email = kakao_account.getAsJsonObject().get("email").getAsString();

        userInfo.put("nickname", nickname);
        userInfo.put("email", email);

        //가져온 사용자 정보를 객체로 만들어서 반환
        return userInfo;
    }

}

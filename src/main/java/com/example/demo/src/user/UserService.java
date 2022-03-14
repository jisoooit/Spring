package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.business.model.PostBusiLocReq;
import com.example.demo.src.business.model.PostBusinessRes;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;


    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;
    }
    /*물품에 관심 누르기*/
    public PostUserRes createProductInterest(PostInterestReq postInterestReq,int userIdxByJwt) throws BaseException {
        //중복
        try{
            int id = userDao.createProductInterest(postInterestReq,userIdxByJwt);
            //jwt 발급.
            //String jwt = jwtService.createJwt(id);
            String jwt="";
            return new PostUserRes(jwt,id);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /*키워드알림등록*/
    public PostKeyWordRes createKeyWord(PostKeyWordReq postKeyWordReq,int userIdxByJwt) throws BaseException {
        //중복
//        if(userProvider.checkKeyWord(postKeyWordReq.getKeyword()) ==1){
//            throw new BaseException(POST_USERS_EXISTS_KEYWORD);
//        }
        try{
//            int userIdxByJwt = jwtService.getUserIdx();
//            System.out.println(userIdxByJwt);
            int id = userDao.createKeyWord(postKeyWordReq,userIdxByJwt);
            //jwt 발급.
//            String jwt = jwtService.createJwt(id);
            String jwt="";
            return new PostKeyWordRes(jwt,id);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /*비즈니스 단골하기*/
    public PostUserRes createBusinessRegular(PostRegularReq PostRegularReq,int userIdxByJwt) throws BaseException {
        //중복
        try{
            int id = userDao.createBusinessRegular(PostRegularReq,userIdxByJwt);
            //jwt 발급.
//            String jwt = jwtService.createJwt(id);
            String jwt="";
            return new PostUserRes(jwt,id);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    //POST
    @Transactional
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        if (postUserReq.getSocial().equals("kakao")) { //한번에로그인할때는 이부분 일단 주석처리해
            System.out.println("카카오회원가입");
            try {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
                String accessToken = request.getHeader("Access-Token");
                System.out.println(accessToken);
                // 액세스 토큰을 활용해서 카카오 유저의 id 확인
                String kakaoId = getUserInfoByToken(accessToken);
                // 로그인 아이디는 kakao_21380328와 같은 형식
                postUserReq.setSocialid("kakao" + kakaoId);
                // 비밀번호는 사실 필요없지만, null값이 들어갈 순 없으므로 일단 kakaoId와 동일하게 세팅
                postUserReq.setPassword(kakaoId);
            } catch (IOException ioException) {
                throw new BaseException(POST_KAKAO_INVALID_TOKEN);
            }
        }
        //중복
        if(userProvider.checkPhone(postUserReq.getPhone()) ==1){
            throw new BaseException(POST_USERS_EXISTS_PHONE);
        }
        if(userProvider.checkSocialId(postUserReq.getSocialid()) ==1){
            throw new BaseException(POST_KAKAO_LOGIN_EXISTS);
        }
        String pwd;
        try{
            //암호화
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPassword());
            postUserReq.setPassword(pwd);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            int id = userDao.createUser(postUserReq);
            //jwt 발급.
//            String jwt = jwtService.createJwt(id);
            String jwt="q";
            return new PostUserRes(jwt,id);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    @Transactional
    public PostLocationRes createLocation(PostLocationReq postLocationReq,int userIdxByJwt) throws BaseException {
        //중복
        try{
//            int id = userDao.createLocation(postLocationReq);
//            //jwt 발급.
//            String jwt="";
//            return new PostUserRes(jwt,id);
            PostLocationRes postLocationRes=userDao.createLocation(postLocationReq,userIdxByJwt);
            return postLocationRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public PostLocationRes createLocationSelect(PostLocSelectReq postLocSelectReq,int userIdxByJwt) throws BaseException {
        //중복
        try{
            PostLocationRes postLocationRes=userDao.createLocationSelect(postLocSelectReq,userIdxByJwt);
            return postLocationRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
        try{
            int result = userDao.modifyUserName(patchUserReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**한번에 로그인 하는거 만들어보고싶었어....
     * code가 프론트 한테 전해진다고 가정하면 이 코드를 controller에 갖다놓으면 파라미터로 postUserReq 받아서 회원가입 정보 추가로 받는것도 가능할듯
     * 못받은건 따로 set으로 넣어주고*/
//    public PostLoginRes loginKakao(String code) throws BaseException, JsonProcessingException {
//        try{
//            String accessToken=getAccessToken(code);
//            String kakaoid="kakao"+getUserInfoByToken(accessToken);
//            int id;
//            if(userProvider.checkSocialId(kakaoid)==1){
//                User user=userDao.kakaoUser(kakaoid);
//                id=user.getId();
//            }else{
//                PostUserReq postUserReq=new PostUserReq(kakaoid,"","","","active","kakao"); //controller에 놓으면 이부분이 달라지겟지 new로 안하고 값을 받을거니까
//                PostUserRes postUserRes=createUser(postUserReq);
//                System.out.println("여기는?");
//                id=postUserRes.getId();
//            }
//            String jwt = jwtService.createJwt(id);
//            return new PostLoginRes(id,jwt);
//        }catch (Exception e){
//            throw new BaseException(POST_KAKAO_LOGIN_EXISTS);
//        }
//
//    }
    public String getAccessToken(String authorizedCode) throws JsonProcessingException {
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
        String accessToken="";
        String tokenJson = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();

        KakaoAccessTokenRes kakaoAccessTokenRes = objectMapper.readValue(tokenJson, KakaoAccessTokenRes.class);
        System.out.println("액세스 토큰임 : "+ kakaoAccessTokenRes.getAccess_token());
        accessToken= kakaoAccessTokenRes.getAccess_token();
        return accessToken;
    }
    public String getUserInfoByToken(String accessToken) throws JsonProcessingException {

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
        String result=response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(result);
        KakaoUserRes kakaoUserRes = objectMapper.readValue(result, KakaoUserRes.class);
        System.out.println("카카오 유저 Idx : "+ kakaoUserRes.getId());
        System.out.println("카카오 유저 닉넴 : "+ kakaoUserRes.getProperties().getNickname());
        System.out.println("카카오 유저 이메일 : "+ kakaoUserRes.getKakao_account().getEmail());
        String kakaoId = kakaoUserRes.getId().toString();

        //가져온 사용자 정보를 객체로 만들어서 반환
        return kakaoId;
    }
}

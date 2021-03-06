package com.example.demo.src.user;

import com.example.demo.src.product.model.GetSaleDetailRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.*;

@RestController
@RequestMapping("/app/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;


    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * 회원 조회 API
     * [GET] /users
     * 회원 번호 및 이메일 검색 조회 API
     * [GET] /users? Email=
     * @return BaseResponse<List<GetUserRes>>
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) String Phone) {
        try{
            if(Phone == null){
                List<GetUserRes> getUsersRes = userProvider.getUsers();
                return new BaseResponse<>(getUsersRes);
            }
            // Get Users
            List<GetUserRes> getUsersRes = userProvider.getUsersByPhone(Phone);
            return new BaseResponse<>(getUsersRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @ResponseBody
    @GetMapping("/page") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetUserRes>> getUsersPaging(@RequestParam(required = false) int page, int limit) {
        try{

            List<GetUserRes> getUsersRes = userProvider.getUsersPaging(page,limit);
            return new BaseResponse<>(getUsersRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 회원 1명 조회 API
     * [GET] /users/:userIdx
     * @return BaseResponse<GetUserRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{id}") // (GET) 127.0.0.1:9000/app/users/:userIdx
    public BaseResponse<GetUserRes> getUser(@PathVariable("id") int id) {
        // Get Users
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            GetUserRes getUserRes = userProvider.getUser(userIdxByJwt);
            return new BaseResponse<>(getUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }
    /**회원 수 구하기*/
    @ResponseBody
    @GetMapping("/cnt")
    public int getUserCnt(){
        try {
            List<GetUserRes> getUsersRes = userProvider.getUsers();
            return getUsersRes.size();
        } catch(BaseException exception){
            return -1;
        }

    }

    /**지역 찾기
     * 아무것도 안주면 전체 지역 조회
     * id가 0이 아닌 값이면 그 id값의 닉네임, 지역조회
     */

    @ResponseBody
    @GetMapping("/location") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetUserLocRes>> getUserLocation(@RequestParam(required = false) Integer id) {
        try{
            if(id == null){
                List<GetUserLocRes> getUserLocRes = userProvider.getLocation();
                return new BaseResponse<>(getUserLocRes);
            }
            // Get Users
            int userIdxByJwt = jwtService.getUserIdx();
            List<GetUserLocRes> getUserLocRes = userProvider.getUserLocation(userIdxByJwt);
            return new BaseResponse<>(getUserLocRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저별 상품 관심 목록*/
    @ResponseBody
    @GetMapping("/interest_list") // (GET) 127.0.0.1:9000/app/users/interest_list/:id
    public BaseResponse<List<GetInterestListRes>> getInterestList() {
        // Get Users
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            List<GetInterestListRes> getInterestListRes = userProvider.getInterestList(userIdxByJwt);
            return new BaseResponse<>(getInterestListRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }
    /**
     * 유저별 받은 쿠폰함*/
    @ResponseBody
    @GetMapping("/couponbox") // (GET) 127.0.0.1:9000/app/users/
    public BaseResponse<List<GetUserCpnRes>> getCouponBox() {
        // Get Users
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            List<GetUserCpnRes> getUserCpnRes = userProvider.getCouponBox(userIdxByJwt);
            return new BaseResponse<>(getUserCpnRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 유저별 등록 키워드*/
    @ResponseBody
    @GetMapping("/keyword") // (GET) 127.0.0.1:9000/app/users/
    public BaseResponse<List<GetKeyWordRes>> getKeyWord() {
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            List<GetKeyWordRes> getKeyWordRes = userProvider.getKeyWord(userIdxByJwt);
            return new BaseResponse<>(getKeyWordRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 유저별 채팅방 목록 - 왜 안되는건지 모르겠네 ㅋ*/
    @ResponseBody
    @GetMapping("/chat") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetUserChatRes>> getUserChat() {
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            List<GetUserChatRes> getUserChatRes = userProvider.getUserChat(userIdxByJwt);
            return new BaseResponse<>(getUserChatRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 키워드 등록*/
    @ResponseBody
    @PostMapping("/keyword")
    public BaseResponse<PostKeyWordRes> createKeyWord(@RequestBody PostKeyWordReq postKeyWordReq) {

        try{
            int userIdxByJwt = jwtService.getUserIdx();
            PostKeyWordRes postKeyWordRes = userService.createKeyWord(postKeyWordReq,userIdxByJwt);
            return new BaseResponse<>(postKeyWordRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 물품에 관심누르기*/
    @ResponseBody
    @PostMapping("/interest")
    public BaseResponse<PostUserRes> createProductInterest(@RequestBody PostInterestReq postInterestReq) {

        try{
            int userIdxByJwt = jwtService.getUserIdx();
            PostUserRes postUserRes = userService.createProductInterest(postInterestReq,userIdxByJwt);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 비즈니스 단골하기*/
    @ResponseBody
    @PostMapping("/regular")
    public BaseResponse<PostUserRes> createBusinessRegular(@RequestBody PostRegularReq postRegularReq) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            PostUserRes postUserRes = userService.createBusinessRegular(postRegularReq,userIdxByJwt);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    @Transactional
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        if(postUserReq.getSocial()==null){
            postUserReq.setSocial("default");
        }
        if(postUserReq.getSocial()=="default"){
            if(postUserReq.getPassword()==null){
                return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
            }
            if(!isRegexPassword(postUserReq.getPassword())){
                return new BaseResponse<>(POST_USERS_INVALID_PASSWORD);
            }
        }
        if(postUserReq.getPhone() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_PHONE);
        }
        if(!isRegexPhone(postUserReq.getPhone())){
            return new BaseResponse<>(POST_USERS_INVALID_PHONE);
        }
        if(postUserReq.getNick()==null){
            return new BaseResponse<>(POST_USERS_EMPTY_NICK);
        }
//        //이메일 정규표현
//        if(!isRegexEmail(postUserReq.getPhone())){
//            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
//        }

        try{
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @ResponseBody
    @PostMapping("/location")
    public BaseResponse<PostLocationRes> createLocation(@RequestBody PostLocationReq postLocationReq) {

        try{
            int userIdxByJwt = jwtService.getUserIdx();
            PostLocationRes postLocationRes = userService.createLocation(postLocationReq,userIdxByJwt);
            return new BaseResponse<>(postLocationRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @ResponseBody
    @PostMapping("/location/select")
    public BaseResponse<PostLocationRes> createLocationSelect(@RequestBody PostLocSelectReq postLocSelectReq) {

        try{
            int userIdxByJwt = jwtService.getUserIdx();
            PostLocationRes postLocationRes = userService.createLocationSelect(postLocSelectReq,userIdxByJwt);
            return new BaseResponse<>(postLocationRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 로그인 API
     * [POST] /users/logIn
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/logIn")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){

        try{
            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
            if(postLoginReq.getPhone() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_PHONE);
            }
            if(!isRegexPhone(postLoginReq.getPhone())){
                return new BaseResponse<>(POST_USERS_INVALID_PHONE);
            }
            if(postLoginReq.getPassword() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
            }
            if(!isRegexPassword(postLoginReq.getPassword())){
                return new BaseResponse<>(POST_USERS_INVALID_PASSWORD);
            }
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**카카오로그인*/
    @ResponseBody
    @GetMapping("/kakaologin")
    public BaseResponse<PostLoginRes> getKakaoUserLogin() {
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String accessToken = request.getHeader("Access-Token");
            if (accessToken == null) {
                return new BaseResponse<>(POST_KAKAO_INVALID_TOKEN);
            }
            String kakaoId = "kakao"+userService.getUserInfoByToken(accessToken);
            PostLoginRes userLoginRes = userProvider.loginKakaoUser(kakaoId);
            return new BaseResponse<>(userLoginRes);
        } catch (IOException ioException) {
            return new BaseResponse<>(POST_KAKAO_INVALID_TOKEN);
        }
        catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    /**
     * 유저정보변경 API
     * [PATCH] /users/:userIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("")
    public BaseResponse<String> modifyUserName(@RequestBody User user){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
//            if(id != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
            //같다면 유저네임 변경
            PatchUserReq patchUserReq = new PatchUserReq(userIdxByJwt,user.getNick());
            userService.modifyUserName(patchUserReq);

            String result = "수정완료";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }



}

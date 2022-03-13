package com.example.demo.src.user;

import com.example.demo.src.product.model.GetSaleDetailRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/oauth")
public class KakaoController {
//    @Autowired
    private UserService userService;
    public KakaoController(UserService userService){
        this.userService = userService;
    }

//
//    @RequestMapping("/kakao/login")
//    public String home(@RequestParam(value = "code", required = false) String code) throws Exception{
//        System.out.println("#########" + code);
//        String access_Token = kakaoService.getAccessToken(code);
//        System.out.println("###access_Token#### : " + access_Token);
//        return "testPage";
//    }

    @GetMapping("/kakao")
    public String home(@RequestParam(value = "code", required = false) String code) throws Exception{
        System.out.println("#########" + code);
        String access_Token = userService.getAccessToken(code);
        String kakaoid=userService.getUserInfoByToken(access_Token);
        return "";
    }

    /*한번에 로그인..*/
//    @GetMapping("/kakao")
//    public BaseResponse<PostLoginRes> home2(@RequestParam(value = "code", required = false) String code) throws Exception{
//        System.out.println("#########" + code);
//        try{
//            PostLoginRes userLoginRes = userService.loginKakao(code);
//            return new BaseResponse<>(userLoginRes);
//        } catch (IOException ioException) {
//            return new BaseResponse<>(POST_KAKAO_INVALID_TOKEN);
//        }
//        catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
}

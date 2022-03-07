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

import java.util.HashMap;
import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/oauth/kakao")
public class KakaoController {
//    @Autowired
    private KakaoService kakaoService;
    public KakaoController(KakaoService kakaoService){
        this.kakaoService = kakaoService;
    }

//
//    @RequestMapping("/kakao/login")
//    public String home(@RequestParam(value = "code", required = false) String code) throws Exception{
//        System.out.println("#########" + code);
//        String access_Token = kakaoService.getAccessToken(code);
//        System.out.println("###access_Token#### : " + access_Token);
//        return "testPage";
//    }

    @GetMapping("")
    public String home(@RequestParam(value = "code", required = false) String code) throws Exception{
        System.out.println("#########" + code);
        String access_Token = kakaoService.getAccessToken(code);
        HashMap<String, Object> userInfo = kakaoService.getUserInfoByToken(access_Token);
        System.out.println("###userInfo#### : " + userInfo.get("email"));
        System.out.println("###nickname#### : " + userInfo.get("nickname"));
        return "";
    }
}

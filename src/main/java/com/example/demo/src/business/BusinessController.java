package com.example.demo.src.business;
import com.example.demo.src.business.model.*;
import com.example.demo.src.product.model.GetSaleDetailRes;
import com.example.demo.src.product.model.PatchProductReq;
import com.example.demo.src.product.model.Product;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/business")
public class BusinessController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final BusinessProvider businessProvider;
    @Autowired
    private final BusinessService businessService;
    @Autowired
    private final JwtService jwtService;


    public BusinessController(BusinessProvider businessProvider, BusinessService businessService, JwtService jwtService){
        this.businessProvider = businessProvider;
        this.businessService = businessService;
        this.jwtService = jwtService;
    }

    /*비즈니스 프로필..*/
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetBusinessRes>> getBusiness(@RequestParam(required = false) Integer id) {
        try{
            if(id == null){
                List<GetBusinessRes> getBusinessRes = businessProvider.getBusiness();
                return new BaseResponse<>(getBusinessRes);
            }
            // Get Users
            List<GetBusinessRes> getBusinessRes = businessProvider.getBusinessById(id);
            return new BaseResponse<>(getBusinessRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 동네가게 소식 페이지 */
    @ResponseBody
    @GetMapping("/news") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetBusiNewsRes>> getBusinessNews(@RequestParam(required = false) String store_name) {
        try{
            if(store_name == null){
                List<GetBusiNewsRes> getBusiNewsRes = businessProvider.getBusinessNews();
                return new BaseResponse<>(getBusiNewsRes);
            }
            List<GetBusiNewsRes> getBusiNewsRes = businessProvider.getBusinessNewsByStore(store_name);
            return new BaseResponse<>(getBusiNewsRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 비즈니스 회원가입*/
    @ResponseBody
    @PostMapping("/profile")
    public BaseResponse<PostBusinessRes> createBusiness(@RequestBody PostBusinessReq postBusinessReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        if(postBusinessReq.getPhone() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_PHONE);
        }
        if(postBusinessReq.getStore_name() == null){
            return new BaseResponse<>(POST_BUSINESS_EMPTY_STORE);
        }
        if(postBusinessReq.getType() == null){
            return new BaseResponse<>(POST_BUSINESS_EMPTY_TYPE);
        }
        if(postBusinessReq.getDetail_type() == null){
            return new BaseResponse<>(POST_BUSINESS_EMPTY_DETAIL_TYPE);
        }
        try{
            PostBusinessRes postBusinessRes = businessService.createBusiness(postBusinessReq);
            return new BaseResponse<>(postBusinessRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @ResponseBody
    @PostMapping("/location")
    public BaseResponse<PostBusinessRes> createBusiLoc(@RequestBody PostBusiLocReq postBusiLocReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!

        try{
            PostBusinessRes postBusinessRes = businessService.createBusiLoc(postBusiLocReq);
            return new BaseResponse<>(postBusinessRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 비즈니스 로그인*/
    @ResponseBody
    @PostMapping("/logIn")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        try{
            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
            PostLoginRes postLoginRes = businessProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**유저 정보 변경*/
    @ResponseBody
    @PatchMapping("/{id}")
    public BaseResponse<String> modifyStoreName(@PathVariable("id") int id, @RequestBody Business business){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(id != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저네임 변경
            PatchBusinessReq patchBusinessReq = new PatchBusinessReq(id,business.getStore_name());
            businessService.modifyStoreName(patchBusinessReq);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 비즈니스 소식 글쓰기*/
    @ResponseBody
    @PostMapping("/news")
    public BaseResponse<PostBusinessRes> createBusiNews(@RequestBody PostBusiNewsReq postBusiNewsReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        if(postBusiNewsReq.getTitle() == null){
            return new BaseResponse<>(POST_PRODUCT_EMPTY_TITLE);
        }
        if(postBusiNewsReq.getContent() == null){
            return new BaseResponse<>(POST_PRODUCT_EMPTY_CONTENT);
        }
        try{
            PostBusinessRes postBusinessRes = businessService.createBusiNews(postBusiNewsReq);
            return new BaseResponse<>(postBusinessRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 비즈니스 소식 수정*/
    @ResponseBody
    @PatchMapping("/news/{bid}")
    public BaseResponse<String> modifyBusinessNews(@PathVariable("bid") int bid, @RequestBody GetBusiNewsRes getBusiNewsRes){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(bid != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저네임 변경
            System.out.println("id확인완료");
            PatchBusiNewsReq PatchBusiNewsReq = new PatchBusiNewsReq(getBusiNewsRes.getNews_num(),getBusiNewsRes.getTitle(),
                    getBusiNewsRes.getContent());
            businessService.modifyBusinessNews(PatchBusiNewsReq);

            String result = "수정완료";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}

package com.example.demo.src.townlife;
import com.example.demo.src.product.ProductProvider;
import com.example.demo.src.product.ProductService;
import com.example.demo.src.product.model.GetSaleDetailRes;
import com.example.demo.src.product.model.GetSalePageRes;
import com.example.demo.src.product.model.PostProductReq;
import com.example.demo.src.product.model.PostProductRes;
import com.example.demo.src.townlife.model.*;
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
@RequestMapping("/app/townlifes")
public class TownlifeController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final TownlifeProvider townlifeProvider;
    @Autowired
    private final TownlifeService townlifeService;
    @Autowired
    private final JwtService jwtService;

    public TownlifeController(TownlifeProvider townlifeProvider, TownlifeService townlifeService, JwtService jwtService){
        this.townlifeProvider = townlifeProvider;
        this.townlifeService = townlifeService;
        this.jwtService = jwtService;
    }

    /**
     * 동네생활화면 api + 카테고리별 조회
     */
//    @ResponseBody
//    @GetMapping("") // (GET) 127.0.0.1:9000/app/townlifes
//    public BaseResponse<List<GetLifePageRes>> getTownlifes() {
//        try{
//            List<GetLifePageRes> getLifePageRes = townlifeProvider.getTownlifes();
//            return new BaseResponse<>(getLifePageRes);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/townlifes
    public BaseResponse<List<GetLifePageRes>> getTownlifes(@RequestParam(required = false) String topic) {
        try{
            if(topic == null){
                List<GetLifePageRes> getLifePageRes = townlifeProvider.getTownlifes();
                return new BaseResponse<>(getLifePageRes);
            }
            List<GetLifePageRes> getLifePageRes = townlifeProvider.getTownlifesByTopic(topic);
            return new BaseResponse<>(getLifePageRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 동네생활글 상세 페이지
     * */
    @ResponseBody
    @GetMapping("/{id}")
    public BaseResponse<List<GetLifeDetailRes>> getTownlife(@PathVariable("id") int id) {
        try{
            List<GetLifeDetailRes> getLifeDetailRes = townlifeProvider.getTownlife(id);
            return new BaseResponse<>(getLifeDetailRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 동네생활글 등록
     * */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostTownlifeRes> createTownlife(@RequestBody PostTownlifeReq postTownlifeReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        try{
            PostTownlifeRes postTownlifeRes = townlifeService.createTownlife(postTownlifeReq);
            return new BaseResponse<>(postTownlifeRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 동네생활글 댓글 등록
     * */
    @ResponseBody
    @PostMapping("/comment")
    public BaseResponse<PostTownlifeRes> createComment(@RequestBody PostCommentReq postCommentReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        try{
            PostTownlifeRes postTownlifeRes = townlifeService.createComment(postCommentReq);
            return new BaseResponse<>(postTownlifeRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}

package com.example.demo.src.product;

import com.example.demo.src.product.ProductProvider;
import com.example.demo.src.product.ProductService;
import com.example.demo.src.product.model.*;
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
@RequestMapping("/app/products")
public class ProductController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ProductProvider productProvider;
    @Autowired
    private final ProductService productService;
    @Autowired
    private final JwtService jwtService;

    public ProductController(ProductProvider productProvider, ProductService productService, JwtService jwtService){
        this.productProvider = productProvider;
        this.productService = productService;
        this.jwtService = jwtService;
    }

    /**
     * 물건 판매 화면 api
     */
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/products
    public BaseResponse<List<GetSalePageRes>> getProducts() {
        try{
            List<GetSalePageRes> getSalePageRes = productProvider.getProducts();
            return new BaseResponse<>(getSalePageRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 물건판매글 1개 상세 api
     * */
    @ResponseBody
    @GetMapping("/{id}") // (GET) 127.0.0.1:9000/app/products/:id (p.id)해도 됨...
    public BaseResponse<List<GetSaleDetailRes>> getProduct(@PathVariable("id") int id) {
        // Get Users
        try{
            List<GetSaleDetailRes> getSaleDetailRes = productProvider.getProduct(id);
            return new BaseResponse<>(getSaleDetailRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 물건판매글 등록
     * */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostProductRes> createProduct(@RequestBody PostProductReq postProductReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        if(postProductReq.getTitle() == null){
            return new BaseResponse<>(POST_PRODUCT_EMPTY_TITLE);
        }
        if(postProductReq.getContent() == null){
            return new BaseResponse<>(POST_PRODUCT_EMPTY_CONTENT);
        }
        if(postProductReq.getPrice()==null){
            return new BaseResponse<>(POST_PRODUCT_EMPTY_PRICE);
        }
        if(postProductReq.getCategory()==null){
            return new BaseResponse<>(POST_PRODUCT_EMPTY_CATEGORY);
        }
        try{
            PostProductRes postProductRes = productService.createProduct(postProductReq);
            return new BaseResponse<>(postProductRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 물건 판매글 수정*/
    @ResponseBody
    @PatchMapping("/{userid}")
    public BaseResponse<String> modifyProduct(@PathVariable("userid") int userid, @RequestBody Product product){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userid != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저네임 변경
            PatchProductReq patchProductReq = new PatchProductReq(product.getId(),product.getCategory(),product.getTitle(),product.getContent(),product.getPrice());
            productService.modifyProduct(patchProductReq);

            String result = "수정완료";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}

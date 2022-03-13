package com.example.demo.src.product;


import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.product.model.PatchProductReq;
import com.example.demo.src.product.model.PostProductReq;
import com.example.demo.src.product.model.PostProductRes;
import com.example.demo.src.product.model.Product;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class ProductService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductDao productDao;
    private final ProductProvider productProvider;
    private final JwtService jwtService;


    @Autowired
    public ProductService(ProductDao productDao, ProductProvider productProvider, JwtService jwtService) {
        this.productDao = productDao;
        this.productProvider = productProvider;
        this.jwtService = jwtService;
    }

    //POST
    public PostProductRes createProduct(PostProductReq postProductReq,int userIdxByJwt) throws BaseException {

        try{
            int id = productDao.createProduct(postProductReq,userIdxByJwt);
            //jwt 발급.
            String jwt="";
            return new PostProductRes(jwt,id);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /*물건 판매글 수정*/
    public void modifyProduct(PatchProductReq patchProductReq) throws BaseException {
        try{

            Product product=productDao.getProductObject(patchProductReq.getUser_id(),patchProductReq.getId());
            patchProductReq.setNullProduct(product);
            System.out.println(product.getContent());
            int result = productDao.modifyProduct(patchProductReq);
            System.out.println(result);
            if(result == 0){
                System.out.println("이거실행");
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(INVALID_USER_JWT);
        }
    }
}

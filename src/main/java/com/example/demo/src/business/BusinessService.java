package com.example.demo.src.business;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.business.model.*;
import com.example.demo.src.product.model.PatchProductReq;
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
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class BusinessService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BusinessDao businessDao;
    private final BusinessProvider businessProvider;
    private final JwtService jwtService;


    @Autowired
    public BusinessService(BusinessDao businessDao, BusinessProvider businessProvider, JwtService jwtService) {
        this.businessDao = businessDao;
        this.businessProvider = businessProvider;
        this.jwtService = jwtService;

    }

    /*비즈니스 회원가입*/
    @Transactional
    public PostBusinessRes createBusiness(PostBusinessReq postBusinessReq) throws BaseException {
        //중복
        if(businessProvider.checkPhone(postBusinessReq.getPhone()) ==1){
            throw new BaseException(POST_USERS_EXISTS_PHONE);
        }
        String pwd;
        try{
            //암호화
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postBusinessReq.getPassword());
            postBusinessReq.setPassword(pwd);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            int id = businessDao.createBusiness(postBusinessReq);
            //jwt 발급.
            String jwt="";
//            String jwt = jwtService.createJwt(id);
            return new PostBusinessRes(jwt,id);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    @Transactional
    public PostBusinessRes createBusiLoc(PostBusiLocReq postBusiLocReq) throws BaseException {
        //중복
        try{
            int id = businessDao.createBusiLoc(postBusiLocReq);
            //jwt 발급.
            String jwt="";
//            String jwt = jwtService.createJwt(id);
            return new PostBusinessRes(jwt,id);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /*비즈니스 소식 글쓰기*/
    public PostBusinessRes createBusiNews(PostBusiNewsReq postBusiNewsReq) throws BaseException {
        //중복
        try{
            int id = businessDao.createBusiNews(postBusiNewsReq);
            //jwt 발급.
            String jwt="";
//            String jwt = jwtService.createJwt(id);
            return new PostBusinessRes(jwt,id);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /*가게이름 바꾸기*/
    public void modifyStoreName(PatchBusinessReq patchBusinessReq) throws BaseException {
        try{
            int result = businessDao.modifyStoreName(patchBusinessReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /*비즈니스 소식 수정*/
    /*물건 판매글 수정*/
    public void modifyBusinessNews(PatchBusiNewsReq patchBusiNewsReq) throws BaseException {
        try{
            int result = businessDao.modifyBusinessNews(patchBusiNewsReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}

package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.business.model.PostBusiLocReq;
import com.example.demo.src.business.model.PostBusinessRes;
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
        //중복
        if(userProvider.checkPhone(postUserReq.getPhone()) ==1){
            throw new BaseException(POST_USERS_EXISTS_PHONE);
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
}

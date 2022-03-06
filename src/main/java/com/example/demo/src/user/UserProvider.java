package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.product.model.GetSaleDetailRes;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    public List<GetUserRes> getUsers() throws BaseException{
        try{
            List<GetUserRes> getUserRes = userDao.getUsers();
            return getUserRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public List<GetUserRes> getUsersByPhone(String phone) throws BaseException{
        if(checkPhone(phone) ==0){
            throw new BaseException(REQUEST_ERROR);
        }
        try{
            List<GetUserRes> getUsersRes = userDao.getUsersByPhone(phone);
            return getUsersRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public GetUserRes getUser(int id) throws BaseException {
        try {
            GetUserRes getUserRes = userDao.getUser(id);
            return getUserRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserLocRes> getLocation() throws BaseException{
        try{
            List<GetUserLocRes> getUserLocRes = userDao.getLocation();
            return getUserLocRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<GetUserLocRes> getUserLocation(int id) throws BaseException{
        try{
            List<GetUserLocRes> getUserLocRes = userDao.getUserLocation(id);
            return getUserLocRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /*회원별 관심목록*/
    public List<GetInterestListRes> getInterestList(int id) throws BaseException {
        try {
            List<GetInterestListRes> getInterestListRes = userDao.getInterestList(id);
            return getInterestListRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /*회원별 받은 쿠폰함*/
    public List<GetUserCpnRes> getCouponBox(int id) throws BaseException {
        try {
            List<GetUserCpnRes> getUserCpnRes = userDao.getCouponBox(id);
            return getUserCpnRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /*회원별 키워드 알림*/
    public List<GetKeyWordRes> getKeyWord(int id) throws BaseException {
        try {
            List<GetKeyWordRes> getKeyWordRes = userDao.getKeyWord(id);
            return getKeyWordRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /*회원별 채팅방목록*/
    public List<GetUserChatRes> getUserChat(int id) throws BaseException{
        try{
            List<GetUserChatRes> getUserChatRes = userDao.getUserChat(id);
            return getUserChatRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /*회원가입할때*/
    public int checkPhone(String phone) throws BaseException{
        try{
            return userDao.checkPhone(phone);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /*키워드 등록할때*/
    public int checkKeyWord(String keyword) throws BaseException{
        try{
            return userDao.checkKeyWord(keyword);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
//    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException{
//        User user = userDao.getPwd(postLoginReq);
//        String password;
//        try {
//            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPassword());
//        } catch (Exception ignored) {
//            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
//        }
//
//        if(postLoginReq.getPassword().equals(password)){
//            int userIdx = userDao.getPwd(postLoginReq).getUserIdx();
//            String jwt = jwtService.createJwt(userIdx);
//            return new PostLoginRes(userIdx,jwt);
//        }
//        else{
//            throw new BaseException(FAILED_TO_LOGIN);
//        }
//
//    }

}

package com.example.demo.src.business;
import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.business.model.GetBusiNewsRes;
import com.example.demo.src.business.model.GetBusinessRes;
import com.example.demo.src.product.model.GetSaleDetailRes;
import com.example.demo.src.user.UserDao;
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
public class BusinessProvider {
    private final BusinessDao businessDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public BusinessProvider(BusinessDao businessDao, JwtService jwtService) {
        this.businessDao = businessDao;
        this.jwtService = jwtService;
    }

    public List<GetBusinessRes> getBusiness() throws BaseException{
        try{
            List<GetBusinessRes> getBusinessRes = businessDao.getBusiness();
            return getBusinessRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<GetBusinessRes> getBusinessById(int id) throws BaseException{
        try{
            List<GetBusinessRes> getBusinessRes = businessDao.getBusinessById(id);
            return getBusinessRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetBusiNewsRes> getBusinessNews() throws BaseException{
        try{
            List<GetBusiNewsRes> getBusiNewsRes = businessDao.getBusinessNews();
            return getBusiNewsRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<GetBusiNewsRes> getBusinessNewsByStore(String store_name) throws BaseException{
        try{
            List<GetBusiNewsRes> getBusiNewsRes = businessDao.getBusinessNewsByStore(store_name);
            return getBusiNewsRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /*회원가입 검사*/
    public int checkPhone(String phone) throws BaseException{
        try{
            return businessDao.checkPhone(phone);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}

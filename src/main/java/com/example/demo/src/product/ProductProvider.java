package com.example.demo.src.product;


import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.product.model.GetSaleDetailRes;
import com.example.demo.src.product.model.GetSalePageRes;
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

@Service
public class ProductProvider {

    private final ProductDao productDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ProductProvider(ProductDao productDao, JwtService jwtService) {
        this.productDao = productDao;
        this.jwtService = jwtService;
    }


    public List<GetSalePageRes> getProducts() throws BaseException{
        try{
            List<GetSalePageRes> getSalePageRes = productDao.getProducts();
            return getSalePageRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetSaleDetailRes> getProduct(int id) throws BaseException {
        try {
            List<GetSaleDetailRes> getSaleDetailRes = productDao.getProduct(id);
            return getSaleDetailRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}

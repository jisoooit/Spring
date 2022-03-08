package com.example.demo.src.townlife;
import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.product.ProductDao;
import com.example.demo.src.product.ProductProvider;
import com.example.demo.src.product.model.PatchProductReq;
import com.example.demo.src.product.model.PostProductReq;
import com.example.demo.src.product.model.PostProductRes;
import com.example.demo.src.townlife.model.PatchTownlifeReq;
import com.example.demo.src.townlife.model.PostCommentReq;
import com.example.demo.src.townlife.model.PostTownlifeReq;
import com.example.demo.src.townlife.model.PostTownlifeRes;
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
public class TownlifeService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TownlifeDao townlifeDao;
    private final TownlifeProvider townlifeProvider;
    private final JwtService jwtService;


    @Autowired
    public TownlifeService(TownlifeDao townlifeDao, TownlifeProvider townlifeProvider, JwtService jwtService) {
        this.townlifeDao = townlifeDao;
        this.townlifeProvider = townlifeProvider;
        this.jwtService = jwtService;
    }

    //POST
    /*동네생활글 등록*/
    public PostTownlifeRes createTownlife(PostTownlifeReq postTownlifeReq) throws BaseException {
        try{
            int id = townlifeDao.createTownlife(postTownlifeReq);
            //jwt 발급.
//            String jwt = jwtService.createJwt(id);
            String jwt="";
            return new PostTownlifeRes(jwt,id);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /*동네생활글 댓글 등록*/
    public PostTownlifeRes createComment(PostCommentReq postCommentReq) throws BaseException {
        try{
            int id = townlifeDao.createComment(postCommentReq);
            //jwt 발급.
//            String jwt = jwtService.createJwt(id);
            String jwt="";
            return new PostTownlifeRes(jwt,id);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /*동네생활글 수정*/
    public void modifyTownlife(PatchTownlifeReq PatchTownlifeReq) throws BaseException {
        try{
            int result = townlifeDao.modifyTownlife(PatchTownlifeReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}

package com.example.demo.src.townlife;
import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.product.ProductDao;
import com.example.demo.src.product.model.GetSaleDetailRes;
import com.example.demo.src.product.model.GetSalePageRes;
import com.example.demo.src.townlife.model.GetLifeDetailRes;
import com.example.demo.src.townlife.model.GetLifePageRes;
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
public class TownlifeProvider {
    private final TownlifeDao townlifeDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public TownlifeProvider(TownlifeDao townlifeDao, JwtService jwtService) {
        this.townlifeDao = townlifeDao;
        this.jwtService = jwtService;
    }

    public List<GetLifePageRes> getTownlifes() throws BaseException{
        try{
            List<GetLifePageRes> getLifePageRes = townlifeDao.getTownlifes();
            return getLifePageRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<GetLifePageRes> getTownlifesByTopic(String topic) throws BaseException{
        try{
            List<GetLifePageRes> getLifePageRes = townlifeDao.getTownlifesByTopic(topic);
            return getLifePageRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public List<GetLifeDetailRes> getTownlife(int id) throws BaseException{
        try{
            List<GetLifeDetailRes> getLifeDetailRes = townlifeDao.getTownlife(id);
            return getLifeDetailRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /*댓글달때 글이 존재하는지 검사*/
    public int checkTownlife(long tid) throws BaseException{
        try{
            return townlifeDao.checkTownlife(tid);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}

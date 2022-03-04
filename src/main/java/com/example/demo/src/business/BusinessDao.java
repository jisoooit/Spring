package com.example.demo.src.business;
import com.example.demo.src.business.model.*;
import com.example.demo.src.product.model.GetSaleDetailRes;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class BusinessDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetBusinessRes> getBusiness(){
        String getBusinessQuery =
                "select * from business\n" +
                "join business_location bl\n" +
                "on business.location_id = bl.id\n" +
                "left join(\n" +
                "    select business_id, count(*) as business_regular_cnt\n" +
                "    from business_regular_user\n" +
                "    group by business_id\n" +
                ") r_cnt\n" +
                "on r_cnt.business_id=business.id\n" +
                "left join(\n" +
                "    select business_id, count(*) as business_news_cnt\n" +
                "    from business_news\n" +
                "    group by business_id\n" +
                ") n_cnt\n" +
                "on n_cnt.business_id=business.id\n" +
                "left join (\n" +
                "    select business_id, count(*) as business_review_cnt\n" +
                "    from business_review\n" +
                "    group by business_id\n" +
                ") rv_cnt\n" +
                "on rv_cnt.business_id=business.id;"; //UserInfo
        return this.jdbcTemplate.query(getBusinessQuery,
                (rs,rowNum) -> new GetBusinessRes(
                        rs.getInt("id"),
                        rs.getString("store_name"),
                        rs.getString("profile_img"),
                        rs.getString("detail_type"),
                        rs.getString("introduction"),
                        rs.getString("town"),
                        rs.getInt("business_regular_cnt"),
                        rs.getInt("business_news_cnt"),
                        rs.getInt("business_review_cnt")
                )
        );
    }

    public List<GetBusinessRes> getBusinessById(int id){
        String getBusinessByIdQuery =
                "select * from business\n" +
                "join business_location bl\n" +
                "on business.id = bl.business_id\n" +
                "left join(\n" +
                "    select business_id, count(*) as business_regular_cnt\n" +
                "    from business_regular_user\n" +
                "    group by business_id\n" +
                ") r_cnt\n" +
                "on r_cnt.business_id=business.id\n" +
                "left join(\n" +
                "    select business_id, count(*) as business_news_cnt\n" +
                "    from business_news\n" +
                "    group by business_id\n" +
                ") n_cnt\n" +
                "on n_cnt.business_id=business.id\n" +
                "left join (\n" +
                "    select business_id, count(*) as business_review_cnt\n" +
                "    from business_review\n" +
                "    group by business_id\n" +
                ") rv_cnt\n" +
                "on rv_cnt.business_id=business.id\n" +
                        "where business.id=?";
        int getBusinessByIdParams = id;
        return this.jdbcTemplate.query(getBusinessByIdQuery,
                (rs,rowNum) -> new GetBusinessRes(
                        rs.getInt("id"),
                        rs.getString("store_name"),
                        rs.getString("profile_img"),
                        rs.getString("detail_type"),
                        rs.getString("introduction"),
                        rs.getString("town"),
                        rs.getInt("business_regular_cnt"),
                        rs.getInt("business_news_cnt"),
                        rs.getInt("business_review_cnt")
                ),
                getBusinessByIdParams);
    }

    public List<GetBusiNewsRes> getBusinessNews(){
        String getBusinessNewsQuery =
                "select *" +
                "from business_news bn\n" +
                "join business b\n" +
                "on bn.business_id = b.id\n" +
                "join business_location bl\n" +
                "on bl.id=b.location_id\n" +
                "left join business_news_Img img\n" +
                "on bn.id = img.business_news_id\n" +
                "order by bn.created_at desc;";
        return this.jdbcTemplate.query(getBusinessNewsQuery,
                (rs,rowNum) -> new GetBusiNewsRes(
                        rs.getInt("bn.id"),
                        rs.getString("store_name"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("bn.created_at"),
                        rs.getString("town"),
                        rs.getString("img")
                )
        );
    }
    public List<GetBusiNewsRes> getBusinessNewsByStore(String store_name){
        String getBusinessNewsByStoreQuery =
                "select *" +
                "from business_news bn\n" +
                "join business b\n" +
                "on bn.business_id = b.id\n" +
                "join business_location bl\n" +
                "on bl.id=b.location_id\n" +
                "left join business_news_Img img\n" +
                "on bn.id = img.business_news_id\n" +
                "where store_name= ?\n" +
                "order by bn.created_at desc";
        String getBusinessNewsByStoreParams = store_name;
        return this.jdbcTemplate.query(getBusinessNewsByStoreQuery,
                (rs, rowNum) -> new GetBusiNewsRes(
                        rs.getInt("bn.id"),
                        rs.getString("store_name"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("bn.created_at"),
                        rs.getString("town"),
                        rs.getString("img")
                ),
                getBusinessNewsByStoreParams);
    }
    /*회원가입*/
    public int createBusiness(PostBusinessReq postBusinessReq){

        String createBusinessQuery = "insert into business(store_name,profile_img,type,detail_type,location_id,phone,introduction,status) VALUES (?,?,?,?,?,?,?,?)";
        Object[] createBusinessParams = new Object[]{postBusinessReq.getStore_name(),postBusinessReq.getProfile_img(),
            postBusinessReq.getType(),postBusinessReq.getDetail_type(),postBusinessReq.getLocation_id(),postBusinessReq.getPhone(),
            postBusinessReq.getIntroduction(),postBusinessReq.getStatus()};

        this.jdbcTemplate.update(createBusinessQuery, createBusinessParams);
       // this.jdbcTemplate.update(createBusiLocQuery, createBusiLocParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }
    public int checkPhone(String Phone){
        String checkPhoneQuery = "select exists(select phone from business where phone = ?)";
        String checkPhoneParams = Phone;
        return this.jdbcTemplate.queryForObject(checkPhoneQuery,
                int.class,
                checkPhoneParams);
    }
    public int createBusiLoc(PostBusiLocReq postBusiLocReq){

          String createBusiLocQuery="insert into business_location(province, city, town, latitude,longitude,business_id,status,country)VALUES (?,?,?,?,?,?,?,?)";
          Object[] createBusiLocParams = new Object[]{postBusiLocReq.getProvince(),postBusiLocReq.getCity(),postBusiLocReq.getTown(),
        postBusiLocReq.getLatitude(),postBusiLocReq.getLongitude(),postBusiLocReq.getBusiness_id(),postBusiLocReq.getStatus(),postBusiLocReq.getCountry()};

        this.jdbcTemplate.update(createBusiLocQuery, createBusiLocParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }
    public int createBusiNews(PostBusiNewsReq postBusiNewsReq){

        String createBusiNewsQuery="insert into business_news(business_id,title, content, status)VALUES (?,?,?,?)";
        Object[] createBusiNewsParams = new Object[]{postBusiNewsReq.getBusiness_id(),postBusiNewsReq.getTitle(),
            postBusiNewsReq.getContent(),postBusiNewsReq.getStatus()};
        this.jdbcTemplate.update(createBusiNewsQuery, createBusiNewsParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }
}

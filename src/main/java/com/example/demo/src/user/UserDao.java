package com.example.demo.src.user;


import com.example.demo.src.business.model.PostBusiLocReq;
import com.example.demo.src.product.model.GetSaleDetailRes;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers(){
        String getUsersQuery = "select * from user"; //UserInfo
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getInt("id"),
                        rs.getString("phone"),
                        rs.getString("nick"),
                        rs.getFloat("manner"),
                        rs.getFloat("retrans_rate"),
                        rs.getFloat("reponse_rate")
//                        rs.getString("status"),
//                        rs.getTimestamp("created_at"),
//                        rs.getTimestamp("updated_at")
                        )
                );
    }

    public List<GetUserRes> getUsersByPhone(String phone){
        String getUsersByPhoneQuery = "select * from user where phone =?";
        String getUsersByPhoneParams = phone;
        return this.jdbcTemplate.query(getUsersByPhoneQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("id"),
                        rs.getString("phone"),
                        rs.getString("nick"),
                        rs.getFloat("manner"),
                        rs.getFloat("retrans_rate"),
                        rs.getFloat("reponse_rate")
//                        rs.getString("status"),
//                        rs.getTimestamp("created_at"),
//                        rs.getTimestamp("updated_at")
                ),
                getUsersByPhoneParams);
    }

    public GetUserRes getUser(int id){
        String getUserQuery = "select * from user where id = ?";
        int getUserParams = id;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("id"),
                        rs.getString("phone"),
                        rs.getString("nick"),
                        rs.getFloat("manner"),
                        rs.getFloat("retrans_rate"),
                        rs.getFloat("reponse_rate")
//                        rs.getString("status"),
//                        rs.getTimestamp("created_at"),
//                        rs.getTimestamp("updated_at")
                ),
                getUserParams);
    }



    public List<GetUserLocRes> getLocation(){
        String getUsersQuery = "select * from user u\n" +
                "join location l\n" +
                "on u.id=l.user_id";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetUserLocRes(
                        rs.getInt("id"),
                        rs.getString("nick"),
                        rs.getString("town")
                )
        );
    }

    public List<GetUserLocRes> getUserLocation(int id){
        String getUsersByIdQuery = "select * from user u\n" +
                "join location l\n" +
                "on u.id=l.user_id where u.id =?";
        int getUsersByIdParams = id;
        return this.jdbcTemplate.query(getUsersByIdQuery,
                (rs, rowNum) -> new GetUserLocRes(
                        rs.getInt("id"),
                        rs.getString("nick"),
                        rs.getString("town")
                ),
                getUsersByIdParams);
    }
    /*회원별 관심물품목록*/
    public List<GetInterestListRes> getInterestList(int id){
        String getInterestListQuery =
                "select *" +
                "from product_interest_user piu\n" +
                "join product p\n" +
                "on piu.product_id = p.id\n" +
                "join user u\n" +
                "on p.user_id=u.id\n" +
                "join user_town ut\n" +
                "on ut.user_id=u.id\n" +
                "join location l\n" +
                "on ut.location_id=l.id\n" +
                "left join( #관심수\n" +
                "    select product_id, count(user_id) as interest_num\n" +
                "    from product_interest_user\n" +
                "    group by product_id ) interest\n" +
                "on p.id = interest.product_id\n" +
                "left join ( #채팅수\n" +
                "    select product_id, count(id) as chat_num\n" +
                "    from chat_room\n" +
                "    group by product_id ) chat\n" +
                "on p.id = chat.product_id\n" +
                "join (\n" +
                "    select product_id, min(img) as img\n" +
                "    from product_img\n" +
                "    group by product_id\n" +
                "    ) p_img\n" +
                "on p.id = p_img.product_id\n" +
                "where piu.user_id=?";
        int getInterestListParams = id;
        return this.jdbcTemplate.query(getInterestListQuery,
                (rs, rowNum) -> new GetInterestListRes(
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getString("town"),
                        rs.getInt("interest_num"),
                        rs.getInt("chat_num"),
                        rs.getString("img")
                ),
                getInterestListParams);
    }

    /*회원별 받은 쿠폰함*/
    public List<GetUserCpnRes> getCouponBox(int id){
        String getCouponBoxQuery =
                "select * from coupon\n" +
                "join business b\n" +
                "on b.id=coupon.business_id\n" +
                "join business_location bl\n" +
                "on bl.id=b.location_id\n" +
                "join user_coupon uc\n" +
                "on coupon.id = uc.coupon_id\n" +
                "where uc.id=?";
        int getCouponBoxParams = id;
        return this.jdbcTemplate.query(getCouponBoxQuery,
                (rs, rowNum) -> new GetUserCpnRes(
                        rs.getString("profile_img"),
                        rs.getDate("end_date"),
                        rs.getInt("discount_amount"),
                        rs.getInt("discount_rate"),
                        rs.getString("store_name"),
                        rs.getString("detail_type"),
                        rs.getString("town")
                ),
                getCouponBoxParams);
    }
    /*회원별 키워드알림*/
    public List<GetKeyWordRes> getKeyWord(int id){
        String getKeyWordQuery =
                "select keyword,town as town_notification\n" +
                "from notification n\n" +
                "join location\n" +
                "on location.id=n.location_id\n" +
                "where n.user_id=?";
        int getKeyWordParams = id;
        return this.jdbcTemplate.query(getKeyWordQuery,
                (rs, rowNum) -> new GetKeyWordRes(
                        rs.getString("keyword"),
                        rs.getString("town_notification")
                ),
                getKeyWordParams);
    }
    /*회원별 채팅방 목록*/
    public List<GetUserChatRes> getUserChat(int id){
        String getUserChatQuery =
                "select  *" +
                "from chat_content\n" +
                "join ( #최근메시지뽑기\n" +
                "    select chat_room_id, max(id) as max_id\n" +
                "    from chat_content\n" +
                "    group by chat_room_id ) last\n" +
                "on max_id = id\n" +
                "join( #내가 들어있는 채팅방, 채팅상대 찾기\n" +
                "    select chat_room.id as room_num,\n" +
                "           case when seller_id=? #내가 seller면 buyer를 상대로 지정\n" +
                "                then buyer_id\n" +
                "                when buyer_id=? #내가 buyer면 seller를 상대로 지정\n" +
                "                then seller_id\n" +
                "           end as other\n" +
                "    from chat_room\n" +
                "    where seller_id = ? || buyer_id=? ) as profile #내(1)가 들어있는 채팅방레코드 다 찾기)\n" +
                "on last.chat_room_id=profile.room_num\n" +
                "join user # 상대의 정보\n" +
                "on user.id=profile.other;";
        int getUserChatParams = id;
        return this.jdbcTemplate.query(getUserChatQuery,
                (rs,rowNum) -> new GetUserChatRes(
                        rs.getLong("room_num"),
                        rs.getString("profile_img"),
                        rs.getString("content"),
                        rs.getTimestamp("create_at"),
                        rs.getString("nick")
                ),
                getUserChatParams,getUserChatParams,getUserChatParams,getUserChatParams);

    }
    /*물품에 관심누르기*/
    public int createProductInterest(PostInterestReq postInterestReq){

        String createProductInterestQuery = "insert into product_interest_user(user_id,product_id,status) VALUES (?,?,?)";
        Object[] createProductInterestParams = new Object[]{postInterestReq.getUser_id(),postInterestReq.getProduct_id(),postInterestReq.getStatus()};
        this.jdbcTemplate.update(createProductInterestQuery, createProductInterestParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    /*키워드 알림 등록*/
    public int createKeyWord(PostKeyWordReq postKeyWordReq,int userIdxByJwt){

        String createKeyWordQuery = "insert into notification(user_id,keyword,location_id,status) VALUES (?,?,?,?)";
        Object[] createKeyWordParams = new Object[]{userIdxByJwt,postKeyWordReq.getKeyword(),postKeyWordReq.getLocation_id(),postKeyWordReq.getStatus()};
        this.jdbcTemplate.update(createKeyWordQuery, createKeyWordParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }
    /*비즈니스 단골하기*/
    public int createBusinessRegular(PostRegularReq postRegularReq){

        String createBusinessRegularQuery = "insert into business_regular_user(user_id,business_id,status,notification_status) VALUES (?,?,?,?)";
        Object[] createBusinessRegularParams = new Object[]{postRegularReq.getUser_id(),postRegularReq.getBusiness_id(),
            postRegularReq.getStatus(),postRegularReq.getNotification_status()};
        this.jdbcTemplate.update(createBusinessRegularQuery, createBusinessRegularParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }
    /*회원 가입*/
    public int createUser(PostUserReq postUserReq){

        String createUserQuery = "insert into user(phone,nick,status,password) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getPhone(), postUserReq.getNick(),postUserReq.getStatus(),postUserReq.getPassword()};

        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }
    /*위치 등록*/
    public PostLocationRes createLocation(PostLocationReq postLocationReq){

        String createLocationQuery="insert into location(province, city, town, latitude,longitude,user_id,status,country)VALUES (?,?,?,?,?,?,?,?)";
        Object[] createLocationParams = new Object[]{postLocationReq.getProvince(),postLocationReq.getCity(),postLocationReq.getTown(),
            postLocationReq.getLatitude(),postLocationReq.getLongitude(),postLocationReq.getUserId(),postLocationReq.getStatus(),postLocationReq.getCountry()};

        this.jdbcTemplate.update(createLocationQuery, createLocationParams);

        String getLocationQuery="select * from location where id=?";
        String lastInserIdQuery = "select last_insert_id()";
        int getLocationParams=this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
        return this.jdbcTemplate.queryForObject(getLocationQuery,
                (rs, rowNum) -> new PostLocationRes(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("town")

                ),
                getLocationParams);
    }
    /*위치 선택*/
    public PostLocationRes createLocationSelect(PostLocSelectReq postLocSelectReq){

        String createLocationSelectQuery="insert into user_town(user_id, location_id, certified_status, status)VALUES (?,?,?,?)";
        Object[] createLocationSelectParams = new Object[]{postLocSelectReq.getUser_id(),postLocSelectReq.getLocation_id(),
            postLocSelectReq.getCertified_status(),postLocSelectReq.getStatus()};

        this.jdbcTemplate.update(createLocationSelectQuery, createLocationSelectParams);

        String getLocationSelectQuery="" +
                "select * from location\n " +
                "join user_town ut on location.id = ut.location_id\n" +
                "where ut.id=?";
        String lastInserIdQuery = "select last_insert_id()";
        int getLocationSelectParams=this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
        System.out.println(getLocationSelectParams);
        return this.jdbcTemplate.queryForObject(getLocationSelectQuery,
                (rs, rowNum) -> new PostLocationRes(
                        rs.getInt("ut.id"),
                        rs.getInt("ut.user_id"),
                        rs.getString("town")
                ),
                getLocationSelectParams);
    }

    public int checkPhone(String Phone){
        String checkPhoneQuery = "select exists(select phone from user where phone = ?)";
        String checkPhoneParams = Phone;
        return this.jdbcTemplate.queryForObject(checkPhoneQuery,
                int.class,
                checkPhoneParams);
    }


    public int checkKeyWord(String keyword){
        String checkPhoneQuery = "select exists(select keyword from notification where keyword = ?)";
        String checkPhoneParams = keyword;
        return this.jdbcTemplate.queryForObject(checkPhoneQuery,
                int.class,
                checkPhoneParams);
    }

    //여기까지

    public int modifyUserName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update user set nick = ? where id = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getNick(), patchUserReq.getId()};
        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

    public User getUser(PostLoginReq postLoginReq){
        String getPwdQuery = "select * from user where phone = ?";
        String getPwdParams = postLoginReq.getPhone();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("id"),
                        rs.getString("phone"),
                        rs.getString("nick"),
                        rs.getFloat("manner"),
                        rs.getFloat("retrans_rate"),
                        rs.getFloat("reponse_rate"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        rs.getString("password")
                ),
                getPwdParams
                );

    }

//    /*유저가 db에 존재하는지 확인*/
//    public int checkUser(int id){
//        String checkPhoneQuery = "select exists(select * from user where id = ?)";
//        int checkPhoneParams = id;
//        return this.jdbcTemplate.queryForObject(checkPhoneQuery,
//                int.class,
//                checkPhoneParams);
//    }

}

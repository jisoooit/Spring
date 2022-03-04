package com.example.demo.src.townlife;

import com.example.demo.src.product.model.GetSaleDetailRes;
import com.example.demo.src.product.model.GetSalePageRes;
import com.example.demo.src.product.model.PostProductReq;
import com.example.demo.src.townlife.model.GetLifeDetailRes;
import com.example.demo.src.townlife.model.GetLifePageRes;
import com.example.demo.src.townlife.model.PostCommentReq;
import com.example.demo.src.townlife.model.PostTownlifeReq;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class TownlifeDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetLifePageRes> getTownlifes(){
        String getTownlifesQuery =
             "select *" +
             "from townlife t\n" +
             "join user u\n" +
             "on t.user_id=u.id\n" +
             "join user_town ut\n" +
             "on ut.user_id=u.id\n" +
             "join location l\n" +
             "on ut.location_id=l.id\n" +
             "join interst_topic\n" +
             "on interst_topic.id = t.interest_topic_id\n" +
             "left join(\n" +
             "    select townlife_id, count(townlife_id) as empathy_num\n" +
             "    from townlife_empathy_user\n" +
             "    group by townlife_id ) empathy_user\n" +
             "on townlife_id = t.id\n" +
             "left join ( #댓글수\n" +
             "    select townlife_id, count(townlife_id) as comment_num\n" +
             "    from townlife_comment_user\n" +
             "    group by townlife_id ) comment_user\n" +
             "on comment_user.townlife_id = t.id;";
        return this.jdbcTemplate.query(getTownlifesQuery,
                (rs,rowNum) -> new GetLifePageRes(
                        rs.getInt("t.id"),
                        rs.getString("topic"),
                        rs.getString("content"),
                        rs.getString("nick"),
                        rs.getString("town"),
                        rs.getTimestamp("t.create_at"),
                        rs.getInt("empathy_num"),
                        rs.getInt("comment_num")
                )
        );
    }

    public List<GetLifePageRes> getTownlifesByTopic(String topic){
        String getTownlifesByTopicQuery =
                "select *" +
                "from townlife t\n" +
                "join user u\n" +
                "on t.user_id=u.id\n" +
                "join user_town ut\n" +
                "on ut.user_id=u.id\n" +
                "join location l\n" +
                "on ut.location_id=l.id\n" +
                "join interst_topic\n" +
                "on interst_topic.id = t.interest_topic_id\n" +
                "left join(\n" +
                "    select townlife_id, count(townlife_id) as empathy_num\n" +
                "    from townlife_empathy_user\n" +
                "    group by townlife_id ) empathy_user\n" +
                "on townlife_id = t.id\n" +
                "left join ( #댓글수\n" +
                "    select townlife_id, count(townlife_id) as comment_num\n" +
                "    from townlife_comment_user\n" +
                "    group by townlife_id ) comment_user\n" +
                "on comment_user.townlife_id = t.id\n" +
                "where topic =?";
        String getTownlifesByTopicParams = topic;
        return this.jdbcTemplate.query(getTownlifesByTopicQuery,
                (rs,rowNum) -> new GetLifePageRes(
                        rs.getInt("t.id"),
                        rs.getString("topic"),
                        rs.getString("content"),
                        rs.getString("nick"),
                        rs.getString("town"),
                        rs.getTimestamp("t.create_at"),
                        rs.getInt("empathy_num"),
                        rs.getInt("comment_num")
                ),
                getTownlifesByTopicParams);
    }


    public List<GetLifeDetailRes> getTownlife(int id){
        String getTownlifeQuery =
                "select distinct *" +
                "from townlife t\n" +
                "join user u\n" +
                "on t.user_id=u.id\n" +
                "join user_town ut\n" +
                "on ut.user_id=u.id\n" +
                "join location l\n" +
                "on ut.location_id=l.id\n" +
                "join interst_topic it\n" +
                "on t.interest_topic_id = it.id\n" +
                "left join(\n" +
                "    select townlife_id, count(townlife_id) as empathy_num\n" +
                "    from townlife_empathy_user\n" +
                "    group by townlife_id ) empathy_user\n" +
                "on townlife_id = t.id\n" +
                "left join ( #댓글수\n" +
                "    select townlife_id, count(townlife_id) as comment_num\n" +
                "    from townlife_comment_user\n" +
                "    group by townlife_id ) comment_user\n" +
                "on comment_user.townlife_id = t.id\n" +
                "left join townlife_comment_user tcu\n" +
                "on t.id = tcu.townlife_id\n" +
                "left join(\n" +
                "    select u.id as user_id,nick as comment_user\n" +
                "    from user u\n" +
                "    join townlife_comment_user tcu on u.id = tcu.user_id\n" +
                ") comment_nick on tcu.user_id=comment_nick.user_id\n" +
                "where t.id=?";
        int getTownlifeParams = id;
        return this.jdbcTemplate.query(getTownlifeQuery,
                (rs, rowNum) -> new GetLifeDetailRes(
                        rs.getInt("t.id"),
                        rs.getString("nick"),
                        rs.getString("topic"),
                        rs.getTimestamp("t.create_at"),
                        rs.getString("t.content"),
                        rs.getString("town"),
                        rs.getInt("empathy_num"),
                        rs.getInt("comment_num"),
                        rs.getString("comment_user"),
                        rs.getString("tcu.content"),
                        rs.getTimestamp("tcu.create_at")
                ),
                getTownlifeParams);
    }

    /*동네 생활글 등록*/
    public int createTownlife(PostTownlifeReq postTownlifeReq){

        String createTownlifeQuery = "insert into townlife(user_id, interest_topic_id,content,status) VALUES (?,?,?,?)";
        Object[] createTownlifeParams = new Object[]{postTownlifeReq.getUser_id(),postTownlifeReq.getInterest_topic_id(),
            postTownlifeReq.getContent(),postTownlifeReq.getStatus()};

        this.jdbcTemplate.update(createTownlifeQuery, createTownlifeParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }
    /*동네 생활글 댓글 등록*/
    public int createComment(PostCommentReq postCommentReq){

        String createCommentQuery = "insert into townlife_comment_user(user_id, townlife_id,status,content) VALUES (?,?,?,?)";
        Object[] createCommentParams = new Object[]{postCommentReq.getUser_id(),postCommentReq.getTownlife_id(),
            postCommentReq.getStatus(),postCommentReq.getContent()};

        this.jdbcTemplate.update(createCommentQuery, createCommentParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }
}

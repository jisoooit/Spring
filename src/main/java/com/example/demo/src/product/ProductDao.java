package com.example.demo.src.product;

import com.example.demo.src.product.model.GetSaleDetailRes;
import com.example.demo.src.product.model.GetSalePageRes;
import com.example.demo.src.product.model.PatchProductReq;
import com.example.demo.src.product.model.PostProductReq;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetSalePageRes> getProducts(){
        String getProductsQuery =
                "select *" +
                "from product p\n" +
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
                "left join product_img img\n" +
                "on p.id = img.product_id;";
        return this.jdbcTemplate.query(getProductsQuery,
                (rs,rowNum) -> new GetSalePageRes(
                        rs.getInt("p.id"),
                        rs.getString("title"),
                        rs.getTimestamp("p.create_at"),
                        rs.getInt("price"),
                        rs.getString("town"),
                        rs.getInt("interest_num"),
                        rs.getInt("chat_num"),
                        rs.getInt("again_status"),
                        rs.getString("img")
                )
        );
    }

    public List<GetSaleDetailRes> getProduct(int id){
        String getProductQuery =
                "select *" +
                "from product p\n" +
                "join user u\n" +
                "on u.id =p.user_id\n" +
                "join user_town t\n" +
                "on t.user_id=u.id\n" +
                "join location l\n" +
                "on l.id=location_id\n" +
                "left join product_img p_img\n" +
                "on p_img.product_id=p.id\n" +
                "left join( \n" +
                "    select product_id, count(user_id) as interest_num\n" +
                "    from product_interest_user\n" +
                "    group by product_id ) interest\n" +
                "on p.id = interest.product_id\n" +
                "left join ( \n" +
                "    select product_id, count(id) as chat_num\n" +
                "    from chat_room\n" +
                "    group by product_id ) chat\n" +
                "on p.id = chat.product_id\n" +
                "where p.id=?";
        int getProductParams = id;
        return this.jdbcTemplate.query(getProductQuery,
                (rs, rowNum) -> new GetSaleDetailRes(
                        rs.getInt("p.id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("category"),
                        rs.getInt("price"),
                        rs.getInt("price_suggest"),
                        rs.getTimestamp("p.create_at"),
                        rs.getFloat("manner"),
                        rs.getString("city"),
                        rs.getString("town"),
                        rs.getInt("interest_num"),
                        rs.getInt("chat_num"),
                        rs.getString("img")
                ),
                getProductParams);
    }


    /*물건판매글 등록*/
    public int createProduct(PostProductReq postProductReq){

        String createProductQuery = "insert into product(user_id,category,title,content,price,status,sale_status,again_status) VALUES (?,?,?,?,?,?,?,?)";
        Object[] createProductParams = new Object[]{postProductReq.getUser_id(),postProductReq.getCategory(),postProductReq.getTitle(),
            postProductReq.getContent(),postProductReq.getPrice(),postProductReq.getStatus(),postProductReq.getSale_status(),postProductReq.getAgain_status()};

        this.jdbcTemplate.update(createProductQuery, createProductParams);

//        String createProductImgQuery=insert into product_img(product_id,img) VALUES (?,?);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }
    /*물건 판매글 수정*/
    public int modifyProduct(PatchProductReq patchProductReq){
        String modifyUserNameQuery = "update product set category=?, title=?, content=?, price=? where id = ? ";
        Object[] modifyUserNameParams = new Object[]{patchProductReq.getCategory(),patchProductReq.getTitle(),patchProductReq.getContent(),patchProductReq.getPrice(),patchProductReq.getId()};
        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

}

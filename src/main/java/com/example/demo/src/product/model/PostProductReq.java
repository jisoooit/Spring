package com.example.demo.src.product.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostProductReq {
    private int user_id;
    private String category;
    private String title;
    private String content;
    private Integer price;
    private int status;
    private int sale_status;
    private int again_status;
//    private List<String> img;
}

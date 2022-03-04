package com.example.demo.src.business.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetBusinessRes {
    private int id;
    private String store_name;
    private String profile_img;
    private String detail_type;
    private String introduction;
    private String town;
    private int business_regular_cnt;
    private int business_news_cnt;
    private int business_review_cnt;
}

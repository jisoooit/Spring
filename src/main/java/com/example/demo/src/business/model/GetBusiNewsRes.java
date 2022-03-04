package com.example.demo.src.business.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetBusiNewsRes {
    private int news_num;
    private String store_name;
    private String title;
    private String content;
    private Timestamp timestamp;
    private String town;
    private String img;
}

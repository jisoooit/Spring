package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetSaleDetailRes {
    private long id;
    private String title;
    private String content;
    private String category;
    private int price;
    private int price_suggest;
    private Timestamp created_at;
    private Float manner;
    private String city;
    private String town;
    private int interest_num;
    private int chat_num;
    private String img;
}

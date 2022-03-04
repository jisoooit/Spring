package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetSalePageRes {
    private long product_num;
    private String product_title;
    private Timestamp timestamp;
    private int price;
    private String town;
    private int interest_num;
    private int chat_num;
    private int again;
    private String img;
}

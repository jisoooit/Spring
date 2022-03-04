package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor

public class Product {
    private long id;
    private int user_id;
    private String category;
    private String title;
    private String content;
    private int price;
    private int price_suggest;
    private int status;
    private Timestamp create_at;
    private Timestamp update_at;
    private int sale_status;
    private int buyer_id;
    private int again_status;

}

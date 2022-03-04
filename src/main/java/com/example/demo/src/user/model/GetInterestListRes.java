package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetInterestListRes {
    private String title;
    private int price;
    private String town;
    private int interest_num;
    private int chat_num;
    private String img;
}

package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetUserCpnRes {
    private String business_img;
    private Date end_date;
    private int discount_amount;
    private int discount_rate;
    private String store_name;
    private String detail_type;
    private String town;

}

package com.example.demo.src.business.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class PostBusiLocReq {
    private String province;
    private String city;
    private String town;
    private Float latitude;
    private Float longitude;
    private int business_id; //이거 없어도되는행인데 db잘못짠듯 ...
    private int status;
    private String country;
}

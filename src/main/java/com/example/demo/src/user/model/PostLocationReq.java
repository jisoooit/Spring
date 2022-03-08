package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class PostLocationReq {
    private String province;
    private String city;
    private String town;
    private Float latitude;
    private Float longitude;
    private int userId;
    private int status;
    private String country;
}

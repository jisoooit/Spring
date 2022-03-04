package com.example.demo.src.business.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class PostBusiNewsReq {
    private int business_id;
    private String title;
    private String content;
    private int status;
}

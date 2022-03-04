package com.example.demo.src.business.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Business {
    private int id;
    private String store_name;
    private String profile_img;
    private String type;
    private String detail_type;
    private int location_id;
    private String phone;
    private String introduction;
    private String status;
    private Timestamp create_at;
    private Timestamp update_at;
}

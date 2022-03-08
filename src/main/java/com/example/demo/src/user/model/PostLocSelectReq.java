package com.example.demo.src.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class PostLocSelectReq {
    private int user_id;
    private int location_id;
    private int certified_status;
    private int status;
}

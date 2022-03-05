package com.example.demo.src.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class PostRegularReq {
    private int user_id;
    private int business_id;
    private int status;
    private int notification_status;
}

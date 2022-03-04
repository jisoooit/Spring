package com.example.demo.src.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class PostKeyWordReq {
    private int user_id;
    private String keyword;
    private int location_id;
    private int status;
}

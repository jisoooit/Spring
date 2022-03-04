package com.example.demo.src.townlife.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostTownlifeReq {
    private int user_id;
    private int interest_topic_id;
    private String content;
    private int status;
}

package com.example.demo.src.townlife.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetLifeDetailRes {
    private long id;
    private String nick;
    private String topic;
    private Timestamp timestamp;
    private String content;
    private String town;
    private int empathy_num;
    private int comment_num;
    private String comment_user;
    private String comment_content;
    private Timestamp comment_timestamp;
}

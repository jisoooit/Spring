package com.example.demo.src.townlife.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetLifePageRes {
    private long townlife_id;
    private String topic;
    private String content;
    private String nickname;
    private String town;
    private Timestamp timestamp;
    private int empathy_num;
    private int comment_num;

}

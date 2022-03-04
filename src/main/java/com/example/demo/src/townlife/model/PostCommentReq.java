package com.example.demo.src.townlife.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostCommentReq {
    private int user_id;
    private long townlife_id;
    private int status;
    private String content;
}

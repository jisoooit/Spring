package com.example.demo.src.townlife.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class PatchTownlifeReq {
    private long id;
    private int user_id;
    private int interest_topic_id;
    private String content;
}

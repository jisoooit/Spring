package com.example.demo.src.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetUserChatRes {
    private long roomNum;
    private String roomImg;
    private String lastMessage;
    private Timestamp timestamp;
    private String roomName;
}

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
    private long room_num;
    private String profile_img;
    private String content;
    private Timestamp create_at;
    private String nick;
}

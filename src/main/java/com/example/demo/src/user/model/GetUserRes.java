package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
    private int id;
    private String phone;
    private String nick;
    private Float manner;
    private Float retrans_rate;
    private Float reponse_rate;
//    private String status;
//    private Timestamp created_at;
//    private Timestamp updated_at;

//    private int userIdx;
//    private String userName;
//    private String ID;
//    private String email;
//    private String password;
}

package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
      private String socialid;
      private String phone;
      private String nick;
//      private Float manner;
//      private Float retrans_rate;
//      private Float reponse_rate;
      private String password;
      private String status;
      private String social;

//      private Timestamp created_at;
//      private Timestamp updated_at;
//    private String UserName;
//    private String id;
//    private String email;
//    private String password;
}

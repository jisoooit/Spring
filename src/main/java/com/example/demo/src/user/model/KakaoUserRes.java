package com.example.demo.src.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserRes {
    private String id;
    private String connected_at;
    private KakaoUserResProperties properties;
    private KakaoUserResAccount kakao_account;
}


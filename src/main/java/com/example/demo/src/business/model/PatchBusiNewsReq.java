package com.example.demo.src.business.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchBusiNewsReq {
    private int id;
    private int business_id;
    private String title;
    private String content;
}

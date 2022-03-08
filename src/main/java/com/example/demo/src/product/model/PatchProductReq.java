package com.example.demo.src.product.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchProductReq {
    private long id;
    private String category;
    private String title;
    private String content;
    private int price;
}

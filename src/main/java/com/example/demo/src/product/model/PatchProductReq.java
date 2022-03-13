package com.example.demo.src.product.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class PatchProductReq {
    private long id;
    private int user_id;
    private String category;
    private String title;
    private String content;
    private Integer price;

    public void setNullProduct(Product product){
        if (this.getCategory()==null){
           // System.out.println("카테고리수정");
            this.setCategory(product.getCategory());
        }
        if(this.getTitle()==null){
         //   System.out.println("제목수정");
            this.setTitle(product.getTitle());
        }
        if(this.getContent()==null){
         //   System.out.println("내용수정");
            this.setContent(product.getContent());
        }
        if(this.getPrice()==0){ //==0으로 해도되나
         //   System.out.println("가격수정");
            this.setPrice(product.getPrice());
        }
    }
}

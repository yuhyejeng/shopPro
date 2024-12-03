package com.example.shoppro.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CartDetailDTO {

    private Long cartItemId;    //장바구니 상품 아이디

    private String itemNm;  //상품명

    private int price;      //상품금액

    private int count;  //수량

    private String imgUrl;  //상품 이미지 경로

    //생성자 // AllArgsConstructor ↓ 같음
    public CartDetailDTO(Long cartItemId, String itemNm,
                         int price, int count, String imgUrl){

        this.cartItemId = cartItemId;
        this.itemNm = itemNm;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }

}

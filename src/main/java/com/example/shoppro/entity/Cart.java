package com.example.shoppro.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString   //(exclude = "itemImgList") // toSrting 변수 제외
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cart")
public class Cart {

    @Id
    @Column(name = "cart_id")       //테이블에서 매핑될 컬럼
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne  //회원 엔티티와 일대일로 매핑
    @JoinColumn(name = "member_id") //매핑할 외래키지정
    private Member member;

    //name 속성에는 매핑할 외래키의 이름을 설정합니다.
    // 설정하지 않으면 JPA가 알아서 id를 찾지만 컴럼명이 우너하는대로
    // 생성되지 않을 수 있기 때문

    // 카트 생성 메소드
    // 이 메소드는 정적메소드로 실행시 Member타입의 객체를 입력받아(파라미터)
    // Cart타입을 반환한다.
    public static Cart createCart(Member member){
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    }

}

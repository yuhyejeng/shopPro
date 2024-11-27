package com.example.shoppro.entity;

import com.example.shoppro.constant.OrderStatus;
import com.example.shoppro.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString   //(exclude = "itemImgList") // toSrting 변수 제외
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem extends BaseTimeEntity {

    @Id
    @Column(name = "order_item_id")       //테이블에서 매핑될 컬럼
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  //회원 엔티티와 일대일로 매핑  // fetch lazy 필요할때만 꺼내옴
    @JoinColumn(name = "order_id") //매핑할 외래키지정
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)  //회원 엔티티와 일대일로 매핑
    @JoinColumn(name = "item_id") //매핑할 외래키지정
    private Item item;

    private int orderPrice;     // 주문가격

    private int count;  //수량


}

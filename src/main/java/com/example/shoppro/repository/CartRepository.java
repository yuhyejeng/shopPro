package com.example.shoppro.repository;

import com.example.shoppro.entity.Cart;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long > {

    //사용자의 email을 통해서 장바구니를 찾는다.
    // Member member = findByEmail(email);
    // cartRepository.findByMemberId(member.id);
    // select * from Cart c where c.member.email

    public Cart findByMember_Id (Long member_id);

    public Cart findByMemberEmail(String email);

}

package com.example.shoppro.repository;

import com.example.shoppro.dto.CartDetailDTO;
import com.example.shoppro.entity.Cart;
import com.example.shoppro.entity.CartItem;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long > {

    // 뭔가 찾는게 필요해 cartrepository 참조할것

    //select * from cartitem c wher c.cart.id = :cartId and c.item.id =#{itemid}

    // 카트의 pk, itemid의 기본키로 cartItem을 찾는다. 내 카트에 담긴 몇번 아이템
    // 내카트에 담긴 아이템 = cartItem
    public CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    public List<CartItem> findByCartId (Long cartId);

    //장바구니 id를 받아와서 1개만있음 이메일당
    // entity로 항상 sql 결과를 반환받았으나 dto도 가능하다.
    @Query("select new com.example.shoppro.dto.CartDetailDTO(ci.id, i.itemNm, i.price, ci.count, im.imgUrl)" +
            " from CartItem ci , ItemImg  im " +
            "join ci.item i  where ci.cart.id = :cartId " +
            " and im.item.id = ci.item.id " +
            " and im.repimgYn = 'Y' " +
            " order by ci.id desc ")
    public List<CartDetailDTO> findByCartDetailDTOList(Long cartId);

}

package com.example.shoppro.repository;

import com.example.shoppro.entity.Cart;
import com.example.shoppro.entity.CartItem;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long > {
}

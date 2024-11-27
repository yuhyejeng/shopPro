package com.example.shoppro.repository;

import com.example.shoppro.entity.Cart;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long > {
}

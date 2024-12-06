package com.example.shoppro.repository;

import com.example.shoppro.entity.BoardCa;
import com.example.shoppro.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category , Long> {




}

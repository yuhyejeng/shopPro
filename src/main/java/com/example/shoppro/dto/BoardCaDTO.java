package com.example.shoppro.dto;

import com.example.shoppro.entity.Category;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString //(exclude = "itemImgList")  //toString 변수 제외할 변수명
@NoArgsConstructor
public class BoardCaDTO {


    private Long id;


    private String title;


    private Long caid;

    private CategoryDTO categoryDTO;



}

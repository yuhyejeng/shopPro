package com.example.shoppro.controller;

import com.example.shoppro.dto.OrderDTO;
import com.example.shoppro.dto.OrderHistDTO;
import com.example.shoppro.entity.Order;
import com.example.shoppro.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Log4j2
public class OrderController {

    private final OrderService orderService;

    //주문하기 //주문하기는 상품의 읽기 페이지에서
    // 사용자가 볼수 있으므로 , 따로 get으로 읽기페이지는 만들지 않는다.
    // 대신 그 페이지에서 보내주는 데이터를 바탕으로 orders, orderItem
    // 에 데이터를 입력하는 역할을 한다
    //2가지 방법
    //1. 일반적인 컨트롤러로 데이터를 받고 다시 주문내역으로 이동한다.
        //일반적인 컨트롤러를 쓰더라도 responseEntity 를 사용하여 rest처럼 데이터만을
        //받을수 있다.
    //2. rest 컨트롤러로 데이터를 받고 다시 주문내역으로 이동한다
    // @RequestBody 쓰면 application/json;  charset = utf-8,
    // 키 : 벨류 중에 키가 "키" 이렇게 들어가야함
    @PostMapping("/order")
    public ResponseEntity order(@Valid OrderDTO orderDTO, BindingResult bindingResult, Principal principal, Long id, Long count){

        //유효성검사
        if (bindingResult.hasErrors()){
            StringBuffer sb = new StringBuffer();   //String

            List<FieldError> fieldErrors = bindingResult.getFieldErrors(); //각 필드 에러

            for (FieldError fieldError : fieldErrors){
                log.info("필드 :" + fieldError.getField() + "메시지 : " + fieldError.getDefaultMessage());
                sb.append(fieldError.getDefaultMessage());

            }
            log.info(sb.toString());
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        //주문은 로그인한 사용자 물론 판매자또한 다른 아이템을 자기아이템을
        // 살수 있다. 로그인한 사람만 이 주소로 들어올수 있다. 시큐리티에서 확인
        // 또는 들어올때 principal!=null 이라면 로그인을 한 사람이다.

        log.info("사용자가 현재 주문하려는 내용 " + orderDTO);

        // 저장을 해야한다.
        Long result =
        orderService.order(orderDTO, principal.getName());

        if (result == null){
            return new ResponseEntity<String>("주문수량이 판매가능수량보다 많습니다.", HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<String>("주문완료", HttpStatus.OK);


    }

    @GetMapping({"/orders", "/orders/{page}" })   //두개 쓸 때  "/orders/{page}" }
    public String orderHist(@PathVariable("page")Optional<Integer> Page ,
                            Principal principal, Model model){

        log.info("진입");
        if (principal == null){
            log.info("로그인이 필요함");
            log.info("로그인이 필요함");
            log.info("로그인이 필요함");
            log.info("로그인이 필요함");

            return "redirect:/members/login";
        }

        Pageable pageable = PageRequest.of(Page.isPresent() ? Page.get(): 0, 4);
        log.info(pageable);

        String email = principal.getName();

        Page<OrderHistDTO> orderHistDTOPage =
        orderService.getOrderList(email, pageable);
        //페이징처리에 필요하던것들 start end next pre t/f  total

        model.addAttribute("orders", orderHistDTOPage);
        //html 들어가서 getContent() 함수 호출
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "order/orderHist";
    }




}

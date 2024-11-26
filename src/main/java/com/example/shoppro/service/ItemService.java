package com.example.shoppro.service;

import com.example.shoppro.dto.ItemDTO;
import com.example.shoppro.dto.PageRequestDTO;
import com.example.shoppro.dto.PageResponseDTO;
import com.example.shoppro.entity.Item;
import com.example.shoppro.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ItemService {

    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    //이미지 등록할 itemimgservice 의존성추가
    private final ItemImgService itemImgService;


    //상품등록
    public Long saveItem(ItemDTO itemDTO , List<MultipartFile> multipartFiles) throws IOException {

        //아이템dto를 엔티티로 변환
        Item item = modelMapper.map(itemDTO, Item.class);

        item =
                itemRepository.save(item);

        //이미지등록 추가 할예정
        itemImgService.saveImg(item.getId(),multipartFiles );


        return item.getId();




    }


    public ItemDTO read(Long id){


        Item item =
                itemRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        ItemDTO itemDTO = modelMapper.map(item, ItemDTO.class)
                .setItemImgDTOList(item.getItemImgList());



        return itemDTO;
    }
    public ItemDTO read(Long id, String email){


        Item item =
                itemRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        ItemDTO itemDTO = modelMapper.map(item, ItemDTO.class)
                .setItemImgDTOList(item.getItemImgList());



        return itemDTO;
    }




    public PageResponseDTO<ItemDTO> list(PageRequestDTO pageRequestDTO, String email) {

        //일단 기본으로 전부 가져오기
//        List<Item> list =
//        itemRepository.findAll();
//
//        List<ItemDTO> itemDTOList =
//        list.stream().map(
//                item -> modelMapper.map(item , ItemDTO.class)
//        ).collect(Collectors.toList());
//
//        return itemDTOList;


//        itemRepository.getAdminItemPage();
        Pageable pageable = pageRequestDTO.getPageable("id");
        Page<Item> items =
                itemRepository.getAdminItemPage(pageRequestDTO, pageable, email);
        List<ItemDTO> itemDTOPage =
                items.getContent().stream().map(item -> modelMapper.map(item, ItemDTO.class))
                        .collect(Collectors.toList());

        PageResponseDTO<ItemDTO> itemDTOPageResponseDTO
                = PageResponseDTO.<ItemDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(itemDTOPage)
                .total((int) items.getTotalElements())
                .build();
        return itemDTOPageResponseDTO;
    }


    public ItemDTO update(ItemDTO itemDTO, Long id, List<MultipartFile> multipartFiles, Integer[] delino, Long mainino) {
        //아이템 수정

        Item item =
        itemRepository.findById(itemDTO.getId())
                .orElseThrow(EntityNotFoundException::new);

        //set
        item.setItemNm(itemDTO.getItemNm());
        item.setPrice(itemDTO.getPrice());
        item.setItemDetail(itemDTO.getItemDetail());
        item.setItemSellStatus(itemDTO.getItemSellStatus());
        item.setStockNumber(itemDTO.getStockNumber());



        //삭제번호가 있다면
        if(delino != null) {
            //삭제 번호가 있다면
            for (Integer ino : delino) {

                if (ino != null && !ino.equals("")) {
                    log.info("삭제할 번호는 ino" +ino);
                    itemImgService.removeimg( ino.longValue() );
                }
            }
        }
        if (mainino == null) {
            //대표이미지가 변경 (itemid로 찾는다. )
            //select  * from item_img where item_id = 450 and repimg_yn='Y';
            //대표이미지를 0번 파일이 y로 하고
            // 기존이미지 대표이미지 삭제 또는 기존대표이미지 url변경
            log.info("대표이미지 변경");


        }else {
            //대표이미지가 변경 X
            // 대표이미지 체크여부가 다 N
            log.info("대표이미지 미변경");

            try {
                itemImgService.update(id, multipartFiles, mainino);

            }catch (IOException e){
                // 리다이렉트 업데이트에 id값 가지고 갈까?
                // TODO: 2024-11-26
            }

        }


        return  null;
    }



















}

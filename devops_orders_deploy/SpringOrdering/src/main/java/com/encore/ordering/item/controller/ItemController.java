package com.encore.ordering.item.controller;

import com.encore.ordering.common.CommonResponse;
import com.encore.ordering.item.Dto.ItemRequset;
import com.encore.ordering.item.Dto.ItemResponse;
import com.encore.ordering.item.Dto.ItemSearchDto;
import com.encore.ordering.item.domain.Item;
import com.encore.ordering.item.service.ItemService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/")
    public String home(){
        return "ok";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/item/create")
    public ResponseEntity<CommonResponse> itemCreate(ItemRequset itemRequset){
        Item item = itemService.create(itemRequset);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED,"item succesfully create", item.getId())
                , HttpStatus.CREATED);
    }
    @GetMapping("/items")
    public ResponseEntity<List<ItemResponse>> items(ItemSearchDto itemSearchDto, Pageable pageable){
        List<ItemResponse> itemResponses  = itemService.findAll(itemSearchDto, pageable);
        return new ResponseEntity<>(itemResponses, HttpStatus.OK);
    }

    @GetMapping("/item/{id}/image")//Image를 줄수있는 url
    public ResponseEntity<Resource> getImage(@PathVariable Long id){
        Resource resource = itemService.getImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/item/{id}/update")
    public ResponseEntity<CommonResponse> itemUpdate(@PathVariable Long id, ItemRequset itemRequset){
        Item item = itemService.update(id,itemRequset);
        return new ResponseEntity<>(
                new CommonResponse(HttpStatus.OK
                ,"item successfully updated "
                , item.getId()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/item/{id}/delete")
    public ResponseEntity<CommonResponse> itemDelete(@PathVariable Long id){
        Item item = itemService.delete(id);

        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED,"item succesfully create", item.getId())
                , HttpStatus.CREATED);
    }


}

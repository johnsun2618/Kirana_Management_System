package com.kirana.management.controller;

import com.kirana.management.dto.StoreOwnerDTO;
import com.kirana.management.service.StoreOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class StoreOwnerController {

    private final StoreOwnerService storeOwnerService;
    @Autowired
    public StoreOwnerController(StoreOwnerService storeOwnerService){
        this.storeOwnerService = storeOwnerService;
    }


    @PostMapping("storeOwner/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StoreOwnerDTO> createStoreOwner(@RequestBody StoreOwnerDTO storeOwnerDTO){
        StoreOwnerDTO storeOwnerDTOCreated = storeOwnerService.createStoreOwner(storeOwnerDTO);
        return new ResponseEntity<>(storeOwnerDTOCreated , HttpStatus.CREATED);
    }
}

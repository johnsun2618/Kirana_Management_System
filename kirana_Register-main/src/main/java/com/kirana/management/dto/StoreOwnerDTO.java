package com.kirana.management.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class StoreOwnerDTO {

    private Integer id;
    private String ownerName;
    private String storeAddress;
    private BigDecimal balanceINR;
}

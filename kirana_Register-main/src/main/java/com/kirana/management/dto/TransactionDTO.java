package com.kirana.management.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionDTO {

    private Long id;

    private String type;
    private BigDecimal amountBeforeTransaction;
    private BigDecimal transactionAmount;
    private BigDecimal balanceAmount;
    private BigDecimal balanceAmountUSD;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}

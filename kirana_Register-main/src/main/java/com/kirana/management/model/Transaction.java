package com.kirana.management.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String type;
    private BigDecimal amountBeforeTransaction;
    private BigDecimal transactionAmount;
    private BigDecimal balanceAmount;

    private BigDecimal balanceAmountUSD;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

}

package com.kirana.management.controller;

import com.kirana.management.dto.TransactionDTO;
import com.kirana.management.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class TransactionController {

    private final TransactionService transactionService;
    @Autowired
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    /**
     * Handles HTTP POST requests to create a new transaction.
     *
     * @param transactionDTO The data transfer object containing transaction information.
     * @return ResponseEntity with the created TransactionDTO and HTTP status ACCEPTED (202).
     * @throws Exception If an error occurs during transaction creation.
     */
    @PostMapping("transaction/new")
    public ResponseEntity<TransactionDTO> newTransaction(@RequestBody TransactionDTO transactionDTO) throws Exception {
        TransactionDTO completeTransaction = transactionService.addNewTransaction(transactionDTO);
        return new ResponseEntity<>(completeTransaction , HttpStatus.ACCEPTED);
    }

    /**
     * Handles HTTP GET requests to retrieve all transactions of a specific type.
     *
     * @param type The type of transactions to retrieve.
     * @return ResponseEntity with a list of TransactionDTOs and HTTP status OK (200).
     */
    @GetMapping("transaction/all/{type}")
    public ResponseEntity<List<TransactionDTO>> allTransactionByType(@PathVariable("type") String type){
        List<TransactionDTO> transactionDTOS = transactionService.allTransactionByType(type);
        return new ResponseEntity<>(transactionDTOS , HttpStatus.OK);
    }

    /**
     * Handles HTTP GET requests to retrieve all transactions on a specific date.
     *
     * @param date The date on which transactions occurred.
     * @return ResponseEntity with a list of TransactionDTOs and HTTP status OK (200).
     */
    @GetMapping("transaction/all/date/{date}")
    public ResponseEntity<List<TransactionDTO>> allTransactionByDate(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<TransactionDTO> transactionDTOS = transactionService.allTransactionByDate(date);
        return new ResponseEntity<>(transactionDTOS , HttpStatus.OK);
    }

}

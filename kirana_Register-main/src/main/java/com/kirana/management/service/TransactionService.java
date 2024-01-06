package com.kirana.management.service;

import com.kirana.management.dto.TransactionDTO;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

//     Adds a new transaction based on the provided {@code TransactionDTO}.

    TransactionDTO addNewTransaction(TransactionDTO transactionDTO) throws Exception;

//     Retrieves a list of transactions based on the specified type.
    List<TransactionDTO> allTransactionByType(String type);


//     Retrieves a list of transactions on the specified date.
    List<TransactionDTO> allTransactionByDate(LocalDate date);
}

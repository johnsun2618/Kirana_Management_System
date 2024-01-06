package com.kirana.management.repository;

import com.kirana.management.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction , Long> {

    /**
     * Retrieves a list of transactions based on the specified type.
     *
     * @param type The type of transactions to retrieve.
     * @return List of transactions matching the specified type.
     */
    List<Transaction> findByType(String type);

    /**
     * Retrieves a list of transactions based on the specified date.
     *
     * @param date The date on which transactions occurred.
     * @return List of transactions on the specified date.
     */
    List<Transaction> findByDate(LocalDate date);

}

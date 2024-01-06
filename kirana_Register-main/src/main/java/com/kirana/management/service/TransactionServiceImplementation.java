package com.kirana.management.service;

import com.kirana.management.dto.TransactionDTO;
import com.kirana.management.model.StoreOwner;
import com.kirana.management.model.Transaction;
import com.kirana.management.repository.StoreOwnerRepository;
import com.kirana.management.repository.TransactionRepository;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImplementation implements TransactionService{

    private final TransactionRepository transactionRepository;
    private final StoreOwnerRepository storeOwnerRepository;
    @Autowired
    public TransactionServiceImplementation(TransactionRepository transactionRepository , StoreOwnerRepository storeOwnerRepository){
        this.transactionRepository = transactionRepository;
        this.storeOwnerRepository =storeOwnerRepository;
    }

//    Adds a new transaction based on the provided {@code TransactionDTO}.

    @Override
    public TransactionDTO addNewTransaction(TransactionDTO transactionDTO) throws Exception {

        Transaction transaction = new Transaction();
        transaction.setType(transactionDTO.getType());

        StoreOwner storeOwner = storeOwnerRepository.findById(1).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND , String.format("Pokemon %d not found" , 1)));

        BigDecimal currentAmount = storeOwner.getBalanceINR();
        BigDecimal transactionAmount = transactionDTO.getTransactionAmount();


        transaction.setAmountBeforeTransaction(currentAmount);
        transaction.setTransactionAmount(transactionAmount);
        transaction.setDate(transactionDTO.getDate());

        String apiResponse = inr_usd_conversion();

        JSONParser jsonParser = new JSONParser(apiResponse);
        LinkedHashMap<String, Object> jsonObject = jsonParser.object();
        String rates = jsonObject.get("rates").toString();

        Map<String, BigDecimal> currencyMap = new HashMap<>();

        StringTokenizer tokenizer = new StringTokenizer(rates, ", }{");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            String[] pair = token.split("=");
            currencyMap.put(pair[0], BigDecimal.valueOf(Double.parseDouble(pair[1])));
        }

        BigDecimal balance;
        if(Objects.equals(transactionDTO.getType(), "credit")){
            balance = currentAmount.add(transactionAmount);
        }
        else {
            balance = currentAmount.subtract(transactionAmount);
            if(balance.compareTo(new BigDecimal(0)) < 0){
                throw new Exception("Insufficient balance");
            }
        }


        transaction.setBalanceAmount(balance);
        storeOwner.setBalanceINR(balance);
        BigDecimal balanceUSD = balance.divide(currencyMap.get("INR") , 5 , RoundingMode.FLOOR);
        transaction.setBalanceAmountUSD(balanceUSD);

        Transaction completedTransaction = transactionRepository.save(transaction);

        return mapToDTO(completedTransaction);

    }

//     Retrieves a list of transactions based on the specified type.
    @Override
    public List<TransactionDTO> allTransactionByType(String type) {
        List<Transaction> transactions = transactionRepository.findByType(type);
        return transactions.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

//     Retrieves a list of transactions on the specified date.
    @Override
    public List<TransactionDTO> allTransactionByDate(LocalDate date){
        List<Transaction> transactions = transactionRepository.findByDate(date);

        return transactions.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

//     Performs INR to USD currency conversion using an external API.
    public String inr_usd_conversion() throws IOException, InterruptedException {
        String url = "https://api.fxratesapi.com/latest";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(request , HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

//     Maps a {@code Transaction} entity to a {@code TransactionDTO}.
    private TransactionDTO mapToDTO(Transaction transaction){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setType(transaction.getType());
        transactionDTO.setAmountBeforeTransaction(transaction.getAmountBeforeTransaction());
        transactionDTO.setTransactionAmount(transaction.getTransactionAmount());
        transactionDTO.setBalanceAmount(transaction.getBalanceAmount());
        transactionDTO.setDate(transaction.getDate());
        transactionDTO.setBalanceAmountUSD(transaction.getBalanceAmountUSD());

        return transactionDTO;
    }

}

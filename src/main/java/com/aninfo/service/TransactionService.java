package com.aninfo.service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;

import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Collection<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> findByCbuId(Long cbu) {
        System.out.println("estoy en el servidor\n");
        return transactionRepository.findTransactionByCbu(cbu);
    }

    public Optional<Transaction> findById(Long transactionID) {
        return transactionRepository.findById(transactionID);
    }

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public void deleteById(Long transactionID) {
        transactionRepository.deleteById(transactionID);
    }

    public Account withdraw(Account account,Double transactionValue){
        if (account.getBalance() < transactionValue) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        account.setBalance(account.getBalance() - transactionValue);
        return account;
    }
    public Account deposit(Account account,Double transactionValue){
        if (transactionValue <= 0) {
            throw new DepositNegativeSumException("Cannot deposit negative sums");
        }
        Double value = Double.valueOf(0);
        if(2000 <= transactionValue){
            value = (transactionValue*0.1);
            if(500 <= value) {
                value = Double.valueOf(500);
            }
        }
        account.setBalance(account.getBalance() + transactionValue + value);
        return account;
    }
}

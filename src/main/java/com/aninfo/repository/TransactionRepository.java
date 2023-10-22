package com.aninfo.repository;

import com.aninfo.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> findTransactionByCbu(Long cbu);

    Transaction findTransactionByTransactionID(Long transactionID);

    @Override
    List<Transaction> findAll();
}

package com.aninfo.integration.cucumber;

import com.aninfo.Memo1BankApp;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.service.AccountService;
import com.aninfo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(classes = Memo1BankApp.class)
@WebAppConfiguration
public class AccountIntegrationServiceTest {

    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;

    Account createAccount(Double balance) {
        return accountService.createAccount(new Account(balance));
    }

    Transaction createTransaction(Account account, double transactionValue, String type) {
        return transactionService.createTransaction(new Transaction(account, transactionValue, type));
    }
    Account transactionWithdraw(Account account,Transaction transaction){
        return transactionService.withdraw(account,transaction.getValue());
    }
    Account transactionDeposit(Account account,Transaction transaction){
        return transactionService.deposit(account,transaction.getValue());
    }
    void saveAccount(Account account){
        accountService.save(account);
    }

    Account findAccount(Account account){return accountService.findAccountByCbu(account.getCbu());}

}

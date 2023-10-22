package com.aninfo;

import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.service.AccountService;
import com.aninfo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@SpringBootApplication
@EnableSwagger2
public class Memo1BankApp {

	@Autowired
	private AccountService accountService;
	@Autowired
	private TransactionService transactionService;
	public static void main(String[] args) {
		SpringApplication.run(Memo1BankApp.class, args);
	}

	@PostMapping("/accounts")
	@ResponseStatus(HttpStatus.CREATED)
	public Account createAccount(@RequestBody Account account) {
		return accountService.createAccount(account);
	}

	@PostMapping("/transactions/{cbu}/withdraw")
	@ResponseStatus(HttpStatus.CREATED)
	public Transaction createTransactionWithdraw(@PathVariable Long cbu, @RequestParam Double transactionValue){
		Account account = accountService.findAccountByCbu(cbu);
		Transaction transaction = new Transaction(account, transactionValue,"Withdraw");
		account = transactionService.withdraw(account,transactionValue);
		return transactionService.createTransaction(transaction);
	}

	@PostMapping("/transactions/{cbu}/deposit")
	@ResponseStatus(HttpStatus.CREATED)
	public Transaction createTransactionDeposit(@PathVariable Long cbu, @RequestParam Double transactionValue){
		Account account = accountService.findAccountByCbu(cbu);
		Transaction transaction = new Transaction(account, transactionValue, "Deposit");
		account = transactionService.deposit(account,transactionValue);
		return transactionService.createTransaction(transaction);
	}

	@GetMapping("/accounts")
	public Collection<Account> getAccounts() {
		return accountService.getAccounts();
	}

	@GetMapping("/transactions")
	public Collection<Transaction> getTransactions() {
		return transactionService.getTransactions();
	}

	@GetMapping("/accounts/{cbu}")
	public ResponseEntity<Account> getAccount(@PathVariable Long cbu) {
		Optional<Account> accountOptional = accountService.findById(cbu);
		return ResponseEntity.of(accountOptional);
	}

	@GetMapping("/transactions/cbu/{cbu}")
	public List<Transaction> getTransactionsByCbu(@PathVariable Long cbu) {
		System.out.println("estoy en el banco\n");
		return transactionService.findByCbuId(cbu);
	}

	@GetMapping("/transactions/{transactionID}")
	public ResponseEntity<Transaction> getTransactionsByTransactionID(@PathVariable Long transactionID) {
		Optional<Transaction> transactionOptional = transactionService.findById(transactionID);
		return ResponseEntity.of(transactionOptional);
	}

	@PutMapping("/accounts/{cbu}")
	public ResponseEntity<Account> updateAccount(@RequestBody Account account, @PathVariable Long cbu) {
		Optional<Account> accountOptional = accountService.findById(cbu);
		System.out.println("estoy aqui\n");
		if (!accountOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		account.setCbu(cbu);
		accountService.save(account);
		return ResponseEntity.ok().build();
	}

/*	@PutMapping("/transaction/{transactionID}")
	public ResponseEntity<Account> updateTransaction(@RequestBody Transaction transaction, @PathVariable Long transactionID) {
		Optional<Transaction> transactionOptional = transactionService.findById(transactionID);

		if (!transactionOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		transaction.setCbu(transactionID);
		transactionService.save(transaction);
		return ResponseEntity.ok().build();
	}*/

	@DeleteMapping("/accounts/{cbu}")
	public void deleteAccount(@PathVariable Long cbu) {
		accountService.deleteById(cbu);
	}
	@DeleteMapping("/transaction/{transactionID}")
	public void deleteTransaction(@PathVariable Long transactionID) {
		transactionService.deleteById(transactionID);
	}
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build();
	}
}

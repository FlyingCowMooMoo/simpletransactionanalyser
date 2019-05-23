package com.gmail.petezpapa;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountManager {

  private Collection<Transaction> transactions;
  private String accountId;

  public AccountManager(Collection<Transaction> transactions, String accountId) {
    this.transactions = transactions;
    init(accountId);
  }

  AccountState getAccountStateForDates(LocalDateTime from, LocalDateTime to) {

    Collection<Transaction> transactionsInRange = transactions.stream()
        .filter(transaction -> isDateWithinRange(from, to, transaction.getCreateAt()))
        .collect(Collectors.toList());
    Collection<Transaction> fromThisAccount = transactionsInRange.stream()
        .filter(t -> t.getFromAccountId().equals(accountId)).collect(Collectors.toList());
    Collection<Transaction> toThisAccount = transactionsInRange.stream()
        .filter(t -> t.getToAccountId().equals(accountId)).collect(Collectors.toList());

    AccountState accountState = new AccountState();

    // Transactions from this account to other accounts, so subtract the amounts from the balance
    fromThisAccount.forEach(t -> {
      accountState.setBalance(accountState.getBalance().add(t.getAmount().negate()));
      accountState.setTransactionCount(accountState.getTransactionCount() + 1);
    });

    // Transactions from other accounts to this account, so add the amounts from the balance
    toThisAccount.forEach(t -> {
      accountState.setBalance(accountState.getBalance().add(t.getAmount()));
      accountState.setTransactionCount(accountState.getTransactionCount() + 1);
    });

    return accountState;
  }


  private void init(String accountId) {
    this.accountId = accountId;
    Map<String, Transaction> allForThisAccount = transactions.stream().filter(
        transaction -> (transaction.getFromAccountId().equals(accountId) || transaction
            .getToAccountId().equals(accountId))).collect(
        Collectors.toMap(Transaction::getTransactionId, transaction -> transaction, (a, b) -> b));

    //Apply any reversals related to this account(remove them and related transactions)
    for (Transaction t : allForThisAccount.values()) {
      if (t.getTransactionType().equals(Transaction.TransactionType.REVERSAL)) {

        //Remove related transaction
        allForThisAccount.remove(t.getRelatedTransaction().getTransactionId());

        //Remove this account
        allForThisAccount.remove(t.getTransactionId());
      }
    }
    this.transactions = allForThisAccount.values();
  }

  /**
   * @param rangeStart The start date of the range
   * @param rangeEnd The end date of the range
   * @param value The date on which to perform the check
   * @return The boolean state of if the provided date falls within the provided range
   */
  private boolean isDateWithinRange(LocalDateTime rangeStart, LocalDateTime rangeEnd,
      LocalDateTime value) {
    return !(value.isBefore(rangeStart) || value.isAfter(rangeEnd));
  }
}

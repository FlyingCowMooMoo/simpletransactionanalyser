package com.gmail.petezpapa;

import java.math.BigDecimal;

/**
 * A POJO to represent the state of the selected account according to the provided time frame
 */
public class AccountState {

  BigDecimal balance;
  int transactionCount;

  public AccountState() {
    this.balance = BigDecimal.ZERO;
    this.transactionCount = 0;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public int getTransactionCount() {
    return transactionCount;
  }

  public void setTransactionCount(int transactionCount) {
    this.transactionCount = transactionCount;
  }
}

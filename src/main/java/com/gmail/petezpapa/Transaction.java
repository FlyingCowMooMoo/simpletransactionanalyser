package com.gmail.petezpapa;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

  private String transactionId;
  private String fromAccountId;
  private String toAccountId;
  private LocalDateTime createAt;
  private BigDecimal amount;
  private TransactionType transactionType;
  private Transaction relatedTransaction;

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getFromAccountId() {
    return fromAccountId;
  }

  public void setFromAccountId(String fromAccountId) {
    this.fromAccountId = fromAccountId;
  }

  public String getToAccountId() {
    return toAccountId;
  }

  public void setToAccountId(String toAccountId) {
    this.toAccountId = toAccountId;
  }

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  public void setCreateAt(LocalDateTime createAt) {
    this.createAt = createAt;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public TransactionType getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(TransactionType transactionType) {
    this.transactionType = transactionType;
  }

  public Transaction getRelatedTransaction() {
    return relatedTransaction;
  }

  public void setRelatedTransaction(Transaction relatedTransaction) {
    this.relatedTransaction = relatedTransaction;
  }

  enum TransactionType {
    PAYMENT,
    REVERSAL
  }
}

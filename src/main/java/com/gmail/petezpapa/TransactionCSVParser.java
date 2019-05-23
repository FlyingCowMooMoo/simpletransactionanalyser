package com.gmail.petezpapa;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

class TransactionCSVParser {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter
      .ofPattern("dd/MM/yyyy HH:mm:ss");
  private String fileName;
  private Map<String, Transaction> records;

  TransactionCSVParser(String fileName) {
    this.fileName = fileName;
    records = new HashMap<>();
  }

  private boolean isValidFile() {
    return new File(fileName).isFile();
  }

  TransactionCSVParser parse() {
    if (!isValidFile()) {
      throw new RuntimeException("The path of the file provided is invalid, please try again");
    }
    // Loop over all the lines in the file, skip the first line since we assume it's the header line
    try (Stream<String> stream = Files.lines(Paths.get(fileName)).skip(1)) {
      stream.forEach(this::parseRecord);
    } catch (IOException e) {
      System.err.println(
          "An unexpected error occurred while parsing the file, please validate your file and try again");
      throw new RuntimeException(e);
    }
    return this;
  }


  /**
   * Parses a single csv record stores in the the collection of this instance. Each line should have
   * upto 6 values 0 - Transaction ID 1 - From Account ID 2 - To Account ID 3 - Transaction Date 4 -
   * Transaction Amount 5 - Transaction Type 6(optional) - Related Transaction ID
   *
   * @param line The string to be split
   */
  private void parseRecord(String line) {
    //Split the line and trim all values to remove any unexpected whitespaces
    String[] values = Arrays.stream(line.split(",")).map(String::trim).toArray(String[]::new);
    Transaction transaction = new Transaction();

    transaction.setTransactionId(values[0]);
    transaction.setFromAccountId(values[1]);
    transaction.setToAccountId(values[2]);
    transaction.setCreateAt(
        LocalDateTime.parse(values[3], FORMATTER));
    transaction.setAmount(new BigDecimal(values[4]));
    transaction.setTransactionType(Transaction.TransactionType.valueOf(values[5]));
    if (values.length > 6 && records.get(values[6]) != null) {
      transaction.setRelatedTransaction(records.get(values[6]));
    }
    records.put(values[0], transaction);
  }

  Collection<Transaction> getAllTransactions() {
    return records.values();
  }
}

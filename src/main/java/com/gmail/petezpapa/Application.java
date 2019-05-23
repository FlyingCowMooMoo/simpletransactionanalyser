package com.gmail.petezpapa;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

public class Application implements Runnable {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter
      .ofPattern("dd/MM/yyyy HH:mm:ss");
  @Parameters(index = "0", description = "The file containing the dataset for this application.")
  private File file;
  @Option(names = {"-fd",
      "--fromdate"}, description = "The start of the period on which to export summary")
  private String fromDate;
  @Option(names = {"-td",
      "--todate"}, description = "The end of the period on which to export summary")
  private String toDate;
  @Option(names = {"-acc",
      "--account"}, description = "The account id of the bank account to get the statement from")
  private String accountId;

  public static void main(String[] args) {
    int exitCode = new CommandLine(new Application()).execute(args);
    System.exit(exitCode);
  }

  private static boolean isDateValid(String date) {
    if (date == null) {
      return false;
    }
    try {
      LocalDateTime.parse(date, FORMATTER);
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  @Override
  public void run() {
    LocalDateTime startDate = null;
    LocalDateTime endDate = null;
    if (!isDateValid(fromDate)) {
      System.out.println(
          "The from date is invalid, the format must in the following format: dd/MM/yyyy HH:mm:ss");
      System.exit(-1);
    } else {
      startDate = LocalDateTime.parse(fromDate, FORMATTER);
    }
    if (!isDateValid(toDate)) {
      System.out.println(
          "The end date is invalid, the format must in the following format: dd/MM/yyyy HH:mm:ss");
      System.exit(-1);
    } else {
      endDate = LocalDateTime.parse(toDate, FORMATTER);
    }

    if (accountId == null) {
      System.out.println("You must provide a valid account id. Use parameter -acc or --account");
      System.exit(-1);
    }

    Collection<Transaction> transactions =
        new TransactionCSVParser(file.getAbsolutePath()).parse().getAllTransactions();

    AccountState accountState = new AccountManager(transactions, accountId)
        .getAccountStateForDates(startDate, endDate);

    System.out.println(
        "Relative balance for the period is: " + formatCurrency(accountState.getBalance()));
    System.out.println("Number of transactions included is: " + accountState.getTransactionCount());
  }

  private String formatCurrency(BigDecimal value) {
    DecimalFormat format = new DecimalFormat("$#,##0.00;-$#,##0.00");
    return format.format(value.doubleValue());
  }
}

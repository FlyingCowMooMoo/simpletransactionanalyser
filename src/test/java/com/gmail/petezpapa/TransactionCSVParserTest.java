package com.gmail.petezpapa;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionCSVParserTest {

  @Test
  public void test() throws Exception {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    Collection<Transaction> transactions =
        new TransactionCSVParser(getSampleFilePath()).parse().getAllTransactions();
    LocalDateTime startDate = LocalDateTime.parse("20/10/2018 12:00:00", formatter);
    LocalDateTime endDate = LocalDateTime.parse("20/10/2018 19:00:00", formatter);
    AccountState accountState = new AccountManager(transactions, "ACC334455")
        .getAccountStateForDates(startDate, endDate);
    assertEquals(1, accountState.getTransactionCount());
    assertEquals(new BigDecimal("-25.00"), accountState.getBalance());
  }

  private static String getSampleFilePath() throws Exception {
    String baseDir = Paths.get("").toAbsolutePath().toString();
    return new File(baseDir, "src/test/java/com/gmail/petezpapa/sample.csv").getCanonicalPath();
  }
}

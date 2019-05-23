

# simpletransactionanalyser

simpletransactionanalyser is a system that analyses financial transaction records.

### Running the app

You will need Java 8 or greater installed on your system

In order to run the application you will need to pass in the input file along with the account id and the start and end dates for the report. The dates are in the following format: dd/MM/yyyy HH:mm:ss 
e.g 20/10/2018 19:00:00

Application parameters
 > <file>   The file containing the dataset for this application.
 
> -acc, --account=<accountId>
               The account id of the bank account to get the statement from
               
> -fd, --fromdate=<fromDate>
               The start of the period on which to export summary
               
>-td, --todate=<toDate>
               The end of the period on which to export summary



## Example
```sh
$ mvnw.cmd exec:java -Dexec.mainClass="com.gmail.petezpapa.Application" -Dexec.args="sample.csv -fd '20/10/2018 12:00:00' --todate '20/10/2018 19:00:00' --account ACC334455"
```

The above example will work on Windows. On UNIX systems, replace 'mvnw.cmd' with 'mvnw'

On the first run, maven will need to download depedencies in order to build the application.

# Dependencies
Junit(https://junit.org/junit5/) for Unit testing
Picocli(picocli.info) command line argument parsing

import java.util.HashMap;

public class Bank {
    String name;
    HashMap<String, Loan> LoanRecords;

    public Bank(String name){
        this.name = name;
        this.LoanRecords = new HashMap<>();
    }
}

import java.util.HashMap;

public class UserActions {
    // for local storage, can be thought as local database tables
    static HashMap<String, Bank> Banks = new HashMap<String, Bank>();
    static HashMap<String, Customer> Customers = new HashMap<String,Customer>();
    public static void getLoan(String bankName, String borrowerName,String principalAmount,String numberOfYears, String rateOfInterest){
        Bank bank;
        Customer customer;

        // check if bank already exists
        if(!Banks.containsKey(bankName)){
            bank = new Bank(bankName);
            Banks.put(bankName,bank);
        } else {
            bank = Banks.get(bankName);
        }

        // check if customer already exists
        if(!Customers.containsKey(borrowerName)){
            customer = new Customer(borrowerName);
            Customers.put(customer.name,customer);
        } else {
            customer = Customers.get(borrowerName);
        }

        // create new loan object i.e. sanction a loan to the customer
        Loan loan = new Loan(bank.name,customer.name,Integer.parseInt(principalAmount),Integer.parseInt(numberOfYears),Integer.parseInt(rateOfInterest));

        // add the record in the bank records
        bank.LoanRecords.put(customer.name,loan);

        //update bank in the banks hashmap
        Banks.put(bank.name,bank);

    }

    public static void makePayment(String bankName, String borrowerName,String lumSumAmount, String emiNo){
        // retrive bank and customer details
        Customer customer = Customers.get(borrowerName);
        Bank bank = Banks.get(bankName);

        // get the loan file (object) of the customer
        Loan loanStatus = bank.LoanRecords.get(customer.name);

        // add the amount into the lumSum payments history
        loanStatus.addLumSumAmount(Integer.parseInt(emiNo),Integer.parseInt(lumSumAmount));

        // update the bank loanRecords
        bank.LoanRecords.put(customer.name,loanStatus);

        // uodate bank in the banks hashmap
        Banks.put(bank.name,bank);
    }

    public static void getBalance(String bankName, String borrowerName, String emiNo){
        // retrive bank and customer details
        Customer customer = Customers.get(borrowerName);
        Bank bank = Banks.get(bankName);

        // get the loan file (object) of the customer
        Loan loanStatus = bank.LoanRecords.get(customer.name);

        // get the balance statement of the customer
        int[] res = loanStatus.getBalance(Integer.parseInt(emiNo));

        // format and return the balance statement
        String balanceStatement = String.format("%s %s %d %d",bank.name,customer.name,res[0],res[1]);
        System.out.println(balanceStatement);
    }
}

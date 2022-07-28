import java.util.*;

public class Loan {
    int principalAmount;
    int numberOfYears;
    int rateOfInterest;
    String bankName;
    String customerName;
    int amountToRepay;
    int totalEMIS;
    int monthlyEMI;
    List<LumSumPayment> lumSumPaymentHistory;
    public Loan(String bankName, String borrowerName, int principalAmount, int numberOfYears, int rateOfInterest){
        this.bankName = bankName;
        customerName = borrowerName;
        if (principalAmount <= 0) {
            throw new IllegalArgumentException("Principal Amount must be greater than zero");
        } else {
            this.principalAmount = principalAmount;
        }

        if(numberOfYears <= 0){
            throw new IllegalArgumentException("Number of Years should be greater than zero");
        } else {
            this.numberOfYears = numberOfYears;
        }

        if(rateOfInterest <= 0 || rateOfInterest > 100){
            throw  new IllegalArgumentException("Interest Rate should be greater than zero and less than 100");
        } else {
            this.rateOfInterest = rateOfInterest;
        }

        int[] res = calculateInterest(principalAmount,numberOfYears,rateOfInterest);
        amountToRepay = res[0];
        totalEMIS = res[1];
        monthlyEMI = res[2];
        lumSumPaymentHistory = new ArrayList<>();
    }

    private int[] calculateInterest(int principalAmount, int numberOfYears, int rateOfInterest) {
        int interest = (principalAmount * numberOfYears * rateOfInterest)/100;
        int amountToRepay = principalAmount + interest;
        int totalEMIs = numberOfYears * 12;
        double emiAmount = (double) amountToRepay /totalEMIs;
        double x = Math.ceil(emiAmount);
        int intEmiAmount = (int) x;
        return new int[] {amountToRepay,totalEMIs,intEmiAmount};
    }

    public void addLumSumAmount(int emiNo, int amount){
        if(emiNo <= 0 || emiNo > totalEMIS){
            throw new IllegalArgumentException("EMI Number out of range");
        }

        if(amount <= 0){
            throw new IllegalArgumentException("Lum Sum Amount should be greater than zero");
        }

        LumSumPayment payment = new LumSumPayment(emiNo, amount);
        lumSumPaymentHistory.add(payment);
    }

    public int[] getBalance(int emiNo) {
        // check if there are any lumSum payments made before the current emiNo
        if(emiNo < 0 || emiNo > totalEMIS){
            throw new IllegalArgumentException("EMI Number out of range");
        }

        int previousPayments = getPaymentHistory(emiNo);
        int amountPaidTillNow;
        int remainingEMIs;

        if (previousPayments == 0) {
            amountPaidTillNow = getAmountPaidByEMIsTillNow(emiNo);
            remainingEMIs = totalEMIS - emiNo;
            return new int[]{amountPaidTillNow, remainingEMIs};

        } else {
            // if there are previous lumSum payments calculate the amount pending and equivalent EMIs
            amountPaidTillNow = previousPayments + getAmountPaidByEMIsTillNow(emiNo);
            remainingEMIs = totalEMIS - emiNo - equivalentEMIs(previousPayments);
        }

        return new int[]{amountPaidTillNow, remainingEMIs };
    }

    private int getPaymentHistory(int emiNo) {
        int previousPayments = 0;
        Collections.sort(lumSumPaymentHistory, sortByEmiNo);

        for(LumSumPayment x: lumSumPaymentHistory){
            if(x.emiNo > emiNo){
                break;
            }
            previousPayments += x.amount;
        }

        return previousPayments;
    }

    // custom comparator to sort by LumSumPayment object by their emi no
    Comparator <LumSumPayment> sortByEmiNo = new Comparator<LumSumPayment>() {
        @Override
        public int compare(LumSumPayment a, LumSumPayment b) {
            return a.emiNo - b.emiNo;
        }
    };

    private int getAmountPaidByEMIsTillNow(int emiNo){
        return monthlyEMI * emiNo;
    }

    private int equivalentEMIs(int payment){
        return payment/monthlyEMI;
    }

}

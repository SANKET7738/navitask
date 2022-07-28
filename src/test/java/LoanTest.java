import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoanTest {

    @Test
    void testNegativePrincipalAmountException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Loan loan = new Loan("bankName","borrowerName", -10, 5,1);
        });
    }

    @Test
    void testNegativeTimeException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Loan loan = new Loan("bankName","borrowerName", 1000, -5,1);
        });
    }

    @Test
    void testNegativeInterestRateException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Loan loan = new Loan("bankName","borrowerName", 1000, 5,-1);
        });
    }

    @Test
    void amountToRepayShouldBeGreaterThanPrincipalAmount(){
        Loan loan = new Loan("bankName","borrowerName", 1000, 5, 2);
        assertTrue(loan.amountToRepay>loan.principalAmount);
    }

    @Test
    void totalEMIsShouldBeTwelveTimesTime(){
        Loan loan = new Loan("bankName","borrowerName", 1000, 2,3);
        assertTrue((loan.numberOfYears * 12 == loan.totalEMIS));
    }

    @Test
    void emiNoShouldBeGreaterThanZero(){
        assertThrows(IllegalArgumentException.class, () -> {
            Loan loan = new Loan("bankName","borrowerName", 1000, 2,3);
            loan.addLumSumAmount(-2,1000);
        });
    }

    @Test
    void amountShouldBeGreaterThanZero(){
        assertThrows(IllegalArgumentException.class, () -> {
            Loan loan = new Loan("bankName","borrowerName", 1000, 2,3);
            loan.addLumSumAmount(2,-1000);
        });
    }

    @Test
    void getBalanceShouldReturnTwoValues(){
        Loan loan = new Loan("bankName","borrowerName", 1000, 2,3);
        int[] res = loan.getBalance(2);
        assertEquals(2,res.length);
    }

}
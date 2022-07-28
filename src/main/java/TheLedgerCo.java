import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TheLedgerCo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // read the file path from input
        // filepath = C:\Users\sanke\OneDrive\Desktop\input.txt
        System.out.println("Enter the path for the input file:");
        String filePath = args[0];

        // Create a new file object
        try {
            File file = new File(filePath);

            sc = new Scanner(file);
            System.out.println("Reading file from Scanner");
            while (sc.hasNextLine()) {
                String inputStr = sc.nextLine();
                String[] inputs = inputStr.split(" ");

                if(inputs[0].equals("LOAN")){
                    UserActions.getLoan(inputs[1],inputs[2],inputs[3],inputs[4],inputs[5]);
                } else if(inputs[0].equals("BALANCE")){
                    UserActions.getBalance(inputs[1],inputs[2],inputs[3]);
                } else if(inputs[0].equals("PAYMENT")){
                    UserActions.makePayment(inputs[1],inputs[2],inputs[3],inputs[4]);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}

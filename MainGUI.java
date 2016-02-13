import java.util.InputMismatchException;
import java.util.Scanner;

public class MainGUI {

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++) {
            Generator g= new Generator();
            System.out.println(g.generatePass(10, 20, false));
        }
        /*
        while(true) {
            try {
                System.out.println("Enter low limit");
                Scanner in = new Scanner(System.in);
                int lowLimit = in.nextInt();
                System.out.println("Enter high limit");
                int highLimit = in.nextInt();
                System.out.println("Gen special?");
                boolean genSpecial = in.nextBoolean();
                Generator g = new Generator();
                System.out.println(g.generatePass(lowLimit, highLimit, genSpecial));
                break;
            } catch (InputMismatchException nfe) {
                System.out.println("How hard is it to type in a number or true/false? You piece of shit.");
            }

        }*/
    }
}

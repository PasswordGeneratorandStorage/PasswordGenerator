import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Generator {

    private Random r;
    private ArrayList<String> words;
    private int numFails;


    public Generator()  {
        numFails = 0;
        File list = new File("src\\AppData\\words.txt");
        r = new Random();
        words = new ArrayList<>();
        try {
            Scanner s = new Scanner(list);
            while(s.hasNext()) {
            words.add(s.nextLine());
        }
        } catch (FileNotFoundException fnf) {
        fnf.printStackTrace();
        }
        r = new Random();
    }

    public int getNumWords() {
        return words.size();
    }

    private String generateWord() {
        String word;
        while(true) {
            word = words.get(r.nextInt(words.size()));
            if(word.length() > 0) {
                break;
            }
        }
        return word;
    }

    /**
     * Generates number of words requested by int passed
     * @param numWords number of words desired
     * @return String of capitalized words
     */
    private String generateWords(int numWords) {
        String words = "";
        for(int i = 0; i < numWords; i++) {
            words = words+capitalize(generateWord());
        }
        return words;
    }

    /*************************
     * @return <char>a random special character.</char>
     ************************/
    private char genSpecial() {
        char[] chars = {'!', '@', '#', '$', '%', '^', '&', '*', '-', '/','?'};
        return chars[r.nextInt(chars.length)];

    }


    /********************************************************************
     * Generates a random assortment of commonly used words.
     * Unless charLimit <= 10, then only two words.
     * @param charLowLimit lowest limit of characters allowed
     * @param charLimit highest limit of characters allowed
     * @param genSpecial specifies whether to generate special character
     * @return randomized password that fits specified criteria.
     ********************************************************************/
    public String generatePass(int charLowLimit, int charLimit, int digitCount, boolean genSpecial) {
        assert charLowLimit != charLimit && charLimit > charLowLimit;
        String password = "";
        //Subtracts number of letters from the limit that are going to be numbers. (Stops too many characters)
        charLimit = charLimit - digitCount;
        int numWords = charLimit / 7;
        boolean correctSize = false;
        while(!correctSize) {
            password = generateWords(numWords);
            if(password.length() < charLimit && password.length() >= charLowLimit) {
                correctSize = true;
            } else {
                numFails++;
            }
        }
        if(genSpecial) {
            password = genSpecial() + password;
        }
        password = password + genDigits(digitCount);
        return password;
    }

    /***********************************************************
     * @param word word you want the first letter capitalized of
     * @return capitalized word.
     ***********************************************************/
    private String capitalize(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

    /******************************************************
     * @param numDigits number of random digits to generate
     * @return a string of random digits
     *****************************************************/
    public String genDigits(int numDigits) {
        String nums = "";
        for(int i = 0; i < numDigits; i++) {
            nums = nums + Integer.toString(r.nextInt(10));
        }
        return nums;
    }

}

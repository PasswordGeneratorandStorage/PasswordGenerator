import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Generator {

    private Random r;
    private ArrayList<String> words;

    public Generator()  {

        String directory = System.getProperty("user.home") + "/PasswordGenerator/AppData/";
        File list = new File(directory + "words.txt");
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
    private String generateWords(int numWords, boolean genSpecial) {


        String words = "";
        if(numWords == 1) {
            String pass = generateWord();
            if(genSpecial) {
                pass = pass + genSpecial();
            }
            return pass;
        }
        char special = genSpecial();
        for(int i = 0; i < numWords; i++) {
            if(genSpecial) {
                if(i == 0) {
                    words = capitalize(generateWord());
                } else {
                    words = words + special + capitalize(generateWord());
                }
            } else {
                words = words + capitalize(generateWord());
            }

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
        if (charLowLimit < charLimit) {
            String password = "";
            //Subtracts number of letters from the limit that are going to be numbers. (Stops too many characters)
            charLimit = charLimit - digitCount;

            int numWords = charLimit / 7;
            boolean correctSize = false;
            while (!correctSize) {
                password = generateWords(numWords, genSpecial);
                if (password.length() <= charLimit && password.length() >= charLowLimit) {
                    correctSize = true;
                }
            }

            password = password + genDigits(digitCount);
            return password;
        }
        return null;
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

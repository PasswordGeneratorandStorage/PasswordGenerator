import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


/*TODO: Decide whats better: generating three random words, or a adverb, adjective and a noun.
 *
 *TODO: @FIXME If the user enters some kind of edge case character amount, say 100 or 1, The generator will infinitely loop. Need to add edge-case handler, or limit user input.
 */

public class Generator {

    private Random r;
    File list;
    private ArrayList<String> words;
    private ArrayList<String> adverbs;
    private ArrayList<String> adjectives;
    private ArrayList<String> nouns;


    public Generator()  {
            list = new File("src\\AppData\\words.txt");
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
        adjectives = new ArrayList<>();
        nouns = new ArrayList<>();
        adverbs = new ArrayList<>();
        String[] filePaths = {"src/AppData/parts/adverbs.txt", "src/AppData/parts/adjectives.txt", "src/AppData/parts/nouns.txt"};
        try{
            list = new File(filePaths[0]);
            Scanner s = new Scanner(list);
            while(s.hasNext()) {
                adverbs.add(s.nextLine());
            }
            list = new File(filePaths[1]);
            s = new Scanner(list);
            while(s.hasNext()) {
                adjectives.add(s.nextLine());
            }
            list = new File(filePaths[2]);
            s = new Scanner(list);
            while(s.hasNext()) {
                nouns.add(s.nextLine());
            }
        } catch(FileNotFoundException fnf) {
            System.out.println(fnf.getMessage());
        }
        r = new Random();

    }


    public String generateWord() {
        return words.get(r.nextInt(words.size()));
    }

    public String generateAdjective() {
        return adjectives.get(r.nextInt(adjectives.size()));
    }

    public String generateNoun() {
        return nouns.get(r.nextInt(nouns.size()));
    }

    public String generateAdverb() {
        return adverbs.get(r.nextInt(adverbs.size() -1 ));
    }

    /*************************
     * @return <char>a random special character.</char>
     ************************/
    public char genSpecial() {
        char[] chars = {'!', '@', '#', '$', '%', '^', '&', '*', '-', '/'};
        return chars[r.nextInt(chars.length)];

    }


    /********************************************************************
     * Generates three of the most commonly used words in english.
     * Unless charLimit <= 10, then only two words.
     * @param charLowLimit lowest limit of characters allowed
     * @param charLimit highest limit of characters allowed
     * @param genSpecial specifies whether to generate special character
     * @return randomized password that fits specified criteria.
     ********************************************************************/
    public String generatePass(int charLowLimit, int charLimit, boolean genSpecial) {
        boolean correctSize = false;
        String[] words = new String[3];
        String passWords = null;
        while(!correctSize) {
            for(int i = 0; i < 3; i++) {
                words[i] = capitalize(generateWord());
            }
            if(charLimit <= 10) {
                passWords = words[0] + words[1];
            } else {
                passWords = words[0] + words[1] + words[2];
            }
            if(passWords.length() >= charLowLimit && passWords.length() < charLimit) {
                correctSize = true;
            }
        }
        char sp;
        if(genSpecial) {
            sp = genSpecial();
            return String.format("%s%s",sp, passWords);
        } else {
            return passWords;
        }
    }

    /*********************************************************************
     * Generates adverb, adjective, and a noun in that order.
     * @param lowLimit lowest limit of characters allowed
     * @param upLimit highest limit of characters allowed
     * @param genSpecial specifies whether to generate special character
     * @return randomized password that fits specified criteria.
     ********************************************************************/
    public String genPass(int lowLimit, int upLimit, boolean genSpecial) {
        String finalPass;
        while(true) {
            if(genSpecial) {
                finalPass = genSpecial() + capitalize(generateAdverb()) + capitalize(generateAdjective()) + capitalize(generateNoun());
            } else {
                finalPass = generateAdverb() + generateAdjective() + generateNoun();
            }
            if(finalPass.length() < upLimit && finalPass.length() >= lowLimit) {
                break;
            }
        }
        return finalPass;
    }

    /***********************************************************
     * @param word word you want the first letter capitalized of
     * @return capitalized word.
     ***********************************************************/
    private String capitalize(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

    /******************************************************
     * @param numNums number of random digits to generate
     * @return a string of random digits
     *****************************************************/
    public String genNums(int numNums) {
        String nums = "";
        for(int i = 0; i <= numNums; i++) {
            nums = nums + Integer.toString(r.nextInt(10));
        }
        return nums;
    }

}

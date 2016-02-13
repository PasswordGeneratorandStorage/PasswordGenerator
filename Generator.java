import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
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

    }

    public Generator(boolean g) {
        adjectives = new ArrayList<>();
        nouns = new ArrayList<>();
        adverbs = new ArrayList<>();
        String[] filePaths = {"src/AppData/parts/adverbs.txt", "src/AppData/parts/adjectives.txt", "src/AppData/parts/nouns.txt"};
        for (int i = 0; i < 3; i ++) {

        }
        list = new File("src\\AppData\\words.txt");
        r = new Random();



    }
    public String generateWord() {
        Random r = new Random();
        int rand = r.nextInt(9000);
        return words.get(rand);
    }
    public char genSpecial() {
        char[] chars = {'!', '@', '#', '$', '%', '^', '&', '*', '-', '/'};
        return chars[r.nextInt(chars.length)];

    }



    public String generatePass(int charLowLimit, int charLimit, boolean genSpecial) {
        boolean correctSize = false;
        String[] words = new String[3];
        String passWords = null;
        while(!correctSize) {
            for(int i = 0; i < 3; i++) {
                words[i] = generateWord();
                char[] chars = words[i].toCharArray();
                //Replaces first letter in word with upper case
                chars[0] = String.format("%s", chars[0]).toUpperCase().charAt(0);
                //Converts char array  back to string.
                words[i] = String.valueOf(chars);
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

    public String genPass(int lowLimit, int upLimit, boolean genSpecial) {
        String finalPass = "";
        return finalPass;
    }

}

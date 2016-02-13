import java.io.File;
import java.io.FileNotFoundException;
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

    public char genSpecial() {
        char[] chars = {'!', '@', '#', '$', '%', '^', '&', '*', '-', '/'};
        return chars[r.nextInt(chars.length)];

    }


    //generatePass generates three random words that are of the 10000 most commonly used words in the english language.
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
    //GenPass generates an adverb, an adjective and a noun, in that order.
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
    private String capitalize(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

}

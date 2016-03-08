import java.io.*;
import java.util.Scanner;

/**
 * Hopefully this shit works.
 */
public class Installer {



    public void install() {

        String USER_HOME = System.getProperty("user.home");

        File dir = new File(USER_HOME+"/PasswordGenerator/AppData/users/");
        dir.mkdirs();

        InputStream file = getClass().getResourceAsStream("words.txt");
        Scanner readIn = null;
        readIn = new Scanner(file);
        try{
            PrintWriter pw = new PrintWriter(new FileWriter(USER_HOME + "/PasswordGenerator/AppData/words.txt"));

            while(readIn.hasNext()) {
                pw.println(readIn.nextLine());
            }
        } catch(IOException fnf) {
            System.out.println(fnf.getMessage());
        }
    }

}

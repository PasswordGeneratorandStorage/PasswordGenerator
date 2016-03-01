/**
 * Created by Myles Haynes on 2/28/2016.
 *
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.security.*;

public class UserList {


    ArrayList<String> userList;
    public UserList() {
        userList = new ArrayList<>();
        Scanner scanner = new Scanner(getClass().getResourceAsStream("AppData/words.txt"));
        //Use scanner to load in list of names
    }

    public boolean doesUserExist(String name) {
        //If the person does exist, it means we have an encrypted file for this person.
        for(String tmpUser: userList) {
            if(tmpUser.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    //TODO: Ian, this is where the encryption should be done, and the file save
    public void saveUser(User user, int key) {
        PrintWriter outputFile = null;
        try {
            outputFile = new PrintWriter("src\\AppData\\users\\" + user.getUsersName()+".db");
        } catch (FileNotFoundException e)
        {
        } finally
        {
            for(Account a:user.getAccounts())
            {
                Encryption encryption = new Encryption();
                String original = (a.getAccountName()+","+a.getUsername()+","+a.getPass());
                outputFile.println(encryption.encode(original, key));
            }
            outputFile.close();
        }

    }
    //TODO: Ian, load, with input of key and name. I'm thinking we add a file with that persons name.
    public User loadUser(String name, int key) {
        User finalUser = new User(name);
        Scanner readDB = null;
        File userFile;
        Encryption decrypt = new Encryption();
        try
        {
            userFile = new File("src\\AppData\\users\\" + name + ".db");
            readDB = new Scanner(userFile);
            while(readDB.hasNext())
            {
                String[] delimited = decrypt.decode(readDB.nextLine(), key).split(",");
                finalUser.addAccount(delimited[0], delimited[1], delimited[2]);
            }
        } catch (FileNotFoundException e)
        {

        }

        return finalUser;
    }
}

/**
 * Created by Myles Haynes on 2/28/2016.
 *
 */

import java.io.*;
import java.util.*;

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

    public void saveUser(User user, int key) {
        PrintWriter outputFile = null;
        try {
            outputFile = new PrintWriter("src\\AppData\\users\\" + user.getUsersName()+".db");
        } catch (FileNotFoundException e)
        {
            System.out.println("Creating user save file");
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
            System.out.println("User does not exist. Creating new user.");
        }

        return finalUser;
    }
}

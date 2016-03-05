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
    }

    public boolean checkIfUserExists(String username) {
        try{
            Scanner fileIn = new Scanner(new FileReader("src\\AppData\\users\\users.db"));
            while(fileIn.hasNext()) {
                //Reads in line.
                String storedUsername = fileIn.nextLine();
                //Splits the username from password hash.
                String[] delim = storedUsername.split(",");
                //Sets string to just username
                storedUsername = delim[0];
                //Encodes entered username, checks if it matches the storedUsername.
                if(storedUsername.equalsIgnoreCase(Encryption.getHash(username))) return true;
            }
        } catch(FileNotFoundException fnf) {
            return false;
        }
        //If we're at this point, they don't exist.
        return false;
    }


    public boolean isCorrectPassword(String username, String password) {
        try{
            Scanner fileIn = new Scanner(new FileReader("src\\AppData\\users\\users.db"));
            while(fileIn.hasNext()) {
                //Reads in line.
                String storedPassword = fileIn.nextLine();
                //Splits the password from password hash.
                String[] delim = storedPassword.split(",");

                //Encodes entered password, checks if it matches the storedPassword.
                if(delim[1].equalsIgnoreCase(Encryption.getHash(password)) && delim[0].equalsIgnoreCase(Encryption.getHash(username))) return true;
            }


        } catch(FileNotFoundException fnf) {
            return false;
        }
        //If we're at this point, they don't exist.
        return false;
    }

    public void saveUser(User user, String pass) {

        int key = Encryption.getKey(pass);

        savePassword(user.getUsersName(), pass);

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
                String original = (a.getAccountName()+","+a.getUsername()+","+a.getPass());
                assert outputFile != null;
                outputFile.println(Encryption.encode(original, key));
            }
            assert outputFile != null;
            outputFile.close();
        }

    }

    private void savePassword(String username, String password)
    {
        try
        {
            FileWriter writer = new FileWriter("src\\AppData\\users\\users.db", true);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println(Encryption.getHash(username) + "," + Encryption.getHash(password));
            printWriter.close();
        } catch (Exception e)
        {
            System.out.println("Database not found");
        }
    }

    public User loadUser(String name, String pass) {

        User finalUser = new User(name, pass);
        Scanner readDB;
        FileReader userFile;
        try
        {
            userFile = new FileReader("src\\AppData\\users\\" + name + ".db");
            readDB = new Scanner(userFile);
            readDB.useDelimiter("\r\n");
            while(readDB.hasNext()) {
                String[] delimited = Encryption.decode(readDB.next(), pass).split(",");
                if(delimited.length == 3) {
                    finalUser.addAccount(delimited[0],delimited[1],delimited[2]);
                }
            }
        } catch (FileNotFoundException e)
        {
            System.out.println("User not found.");
        }


        return finalUser;
    }
}

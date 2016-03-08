/**
 * Created by Myles Haynes on 2/28/2016.
 *
 */

import java.io.*;
import java.util.*;

public class UserList {


    ArrayList<String> userList;
    String DIRECTORY;
    public UserList() {
        userList = new ArrayList<>();
        DIRECTORY = System.getProperty("user.home");
        DIRECTORY = DIRECTORY + "/PasswordGenerator";
        File tmp = new File(DIRECTORY + "/AppData/");
        if(!tmp.exists()) {
            new Installer().install();
            System.out.println("Installed.");
        }
    }

    public boolean checkIfUserExists(String username) {
        try{
            Scanner fileIn = new Scanner(new FileReader(DIRECTORY + "/AppData/users/users.db"));
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
            Scanner fileIn = new Scanner(new FileReader(DIRECTORY + "/AppData/users/users.db"));
            while(fileIn.hasNext()) {
                //Reads in line.
                String storedPassword = fileIn.nextLine();
                //Splits the user and password hash
                String[] delim = storedPassword.split(",");

                //Encodes entered password, checks if it matches the storedPassword, and Encodes username, checks if it matches username.
                if(delim.length == 2) {
                    if (delim[1].equalsIgnoreCase(Encryption.getHash(password)) && delim[0].equalsIgnoreCase(Encryption.getHash(username)))
                        return true;
                }
            }


        } catch(FileNotFoundException fnf) {
            return false;
        }
        //If we're at this point, they don't exist.
        return false;
    }

    /**
     * Saves user to file with user.getUsersName() as the name of file.
     * @param user user to be saved
     * @param pass password to encrypt the file as.
     */
    public void saveUser(User user, String pass) {


        savePassword(user.getUsersName(), pass);

        PrintWriter outputFile = null;
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DIRECTORY + "/AppData/users/" + user.getUsersName() + ".db"), "ASCII"));
            outputFile = new PrintWriter(DIRECTORY + "/AppData/users/" + user.getUsersName() +".db");
        } catch (FileNotFoundException e)
        {
            System.out.println("Creating user save file");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally
        {
            for(Account a:user.getAccounts())
            {
                String original = (a.getAccountName()+","+a.getUsername()+","+a.getPass());
                assert outputFile != null;
                outputFile.println(Encryption.encode(original, pass));
                output.write(Encryption.encode(original, pass));
                output.write("\r\n");
            }
            assert outputFile != null;
            outputFile.close();
        }

    }

    /**
     * Saves password and username to users.db. Encrypts name/password with SHA256
     * @param username username to be added
     * @param password password to be added.
     */
    public void savePassword(String username, String password)
    {
        try
        {
            FileWriter writer = new FileWriter(DIRECTORY + "/AppData/users/users.db", true);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println(Encryption.getHash(username) + "," + Encryption.getHash(password));
            printWriter.close();
        } catch (Exception e)
        {
            System.out.println("Database not found");
        }
    }

    /**
     * Loads in user, decrypts the program using a variation of the ROT13 encryption.
     * Can only handle ASCII values.
     * @param name name of user to be loaded in.
     * @param pass password used to decrypt file.
     * @return a user with all accounts added from file.
     */
    public User loadUser(String name, String pass) {

        User finalUser = new User(name, pass);
        Scanner readDB;
        try
        {
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(DIRECTORY + "/AppData/users/" + name + ".db"), "ASCII"));
            System.out.println("Trying to read from" + DIRECTORY + "/AppData/users/" + name + ".db");
            readDB = new Scanner(input);
            readDB.useDelimiter("\r\n");
            while(readDB.hasNext()) {
                String[] delimited = Encryption.decode(readDB.next(), pass).split(",");
                if(delimited.length == 3) {
                    finalUser.addAccount(delimited[0],delimited[1],delimited[2]);
                }
            }
        } catch (FileNotFoundException|UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage());
            System.out.println("User not found.");
        }


        return finalUser;
    }
}

/**
 * Created by Myles Haynes on 2/28/2016.
 *
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserList {



    ArrayList<String> userList;
    String DIRECTORY;
    public UserList() {
        userList = new ArrayList<>();
        DIRECTORY = System.getProperty("user.home");
        DIRECTORY = DIRECTORY + "/PasswordGenerator/";
        File tmp = new File(DIRECTORY + "AppData/");
        if(!tmp.exists()) {
            new Installer().install();
        }
    }

    public boolean checkIfUserExists(String username) {
        try{
            Scanner fileIn = new Scanner(new FileReader(DIRECTORY + "/AppData/users/users.txt"));
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
            Scanner fileIn = new Scanner(new FileReader(DIRECTORY + "/AppData/users/users.txt"));
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

        String fileName = DIRECTORY + "AppData/users/" + user.getUsersName() + ".db";

        User encryptedUser = encryptUser(user, pass);


        try {
            File file = new File(fileName);
            ObjectOutputStream userOut = new ObjectOutputStream(new FileOutputStream(file));
            userOut.writeObject(encryptedUser);
            decryptUser(user, pass);
            userOut.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private User encryptUser(User user, String pass) {

        for(Account a: user.getAccounts()) {
            String encryptedAccount = Encryption.encode(a.getAccountName(), pass);
            String encryptedUserName = Encryption.encode(a.getUsername(), pass);
            String encryptedPass = Encryption.encode(a.getPass(), pass);

            a.setAccountName(encryptedAccount);
            a.setUsername(encryptedUserName);
            a.setPass(encryptedPass);
        }
        return user;
    }

    private User decryptUser(User user, String pass) {
        assert user !=null && pass !=null;
        for(Account a: user.getAccounts()) {

            String decryptedAccount = Encryption.decode(a.getAccountName(), pass);
            String decryptedUserName = Encryption.decode(a.getUsername(), pass);
            String decryptedPass = Encryption.decode(a.getPass(), pass);

            a.setAccountName(decryptedAccount);
            a.setUsername(decryptedUserName);
            a.setPass(decryptedPass);
        }
        return user;
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
            FileWriter writer = new FileWriter(DIRECTORY + "/AppData/users/users.txt", true);
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

        String fileName = DIRECTORY + "AppData/users/" + name + ".db";
        User user = null;
        try {
            ObjectInputStream userIn = new ObjectInputStream(new FileInputStream(fileName));
            user = (User) userIn.readObject();
        } catch(IOException|ClassNotFoundException io) {
            System.out.println("Failed to load.");
        }
        decryptUser(user, pass);
        return user;

    }
}

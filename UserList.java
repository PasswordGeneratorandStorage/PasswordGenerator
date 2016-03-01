/**
 * Created by Myles Haynes on 2/28/2016.
 *
 */
import java.util.ArrayList;
import java.util.Scanner;

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
    public void saveUser(User user, String key) {
        //Inputs user, and encrypts with key implement however you'd like:)
    }
    //TODO: Ian, load, with input of key and name. I'm thinking we add a file with that persons name.
    public User loadUser(String name, String key) {
        //Inputs name, and encrypts with key string.
        return null;
    }
}

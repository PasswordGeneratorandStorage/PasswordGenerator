import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class PasswordGeneratorApp {
    public static void main(String[] args) {
        Generator g = new Generator();
        Scanner userIn = new Scanner(System.in);
        System.out.println("Please choose:\n1)Log me in.\n2)New User");
        int choice = userIn.nextInt();
        if(choice == 1) {
            ArrayList<String> userlist = new ArrayList<>(5);
            try {
                File userlistFile = new File("src/AppData/users/userlist.txt");
                Scanner scan = new Scanner(userlistFile);
                scan.useDelimiter("\\r\\n");
                while (scan.hasNext()) {
                    userlist.add(scan.next());
                }
            } catch (Exception fnf) {
                System.out.println("You suck");
            }
            boolean foundAccount = false;
            System.out.println("Please enter your username");
            String username = null;
            while (!foundAccount) {
                String enteredName = userIn.next();
                for (String u : userlist) {
                    if (enteredName.equalsIgnoreCase(u)) {
                        foundAccount = true;
                        username = enteredName;
                    }
                }
                if (!foundAccount) {
                    System.out.println("Couldn't find you. Please re-enter your username.");
                    enteredName = userIn.next();
                }
            }
            User user = loadUser(username);
            if(user != null) {
                System.out.println(user.getUsersName());
            } else {
                System.out.println("Is Null");
            }

        } else {
            boolean usernameMatches = false;
            User newUser = null;
            while(!usernameMatches) {
                System.out.println("Please enter your name. This will be your username.");
                String selectedName = userIn.next();
                System.out.println("Please re-enter, to make sure there are no mistakes.(Case doesn't matter)");
                String selected2Name = userIn.next();
                if (selected2Name.equalsIgnoreCase(selectedName)) {
                    usernameMatches = true;
                    addUserToList(selectedName);
                    newUser = addUser(selectedName);
                } else {
                    System.out.println("Doesn't match, try again.");
                }
            }
            System.out.println("Do you want to add an account?");
            String selection = userIn.next();
            switch(selection.toLowerCase()) {
                case"yes":case"y":
                    System.out.println("What account is this for?");
                    String accountName = userIn.next();
                    System.out.println("We're going to generate a password for you.\nPlease enter your minimum character limit");
                    int charLowLimit = userIn.nextInt();
                    System.out.println("Got it, what about the maxField character limit?");
                    int charLimit = userIn.nextInt();
                    System.out.println("Do you want to have a special character?");
                    String spCharacter = userIn.next();
                    boolean wantsSpCharacter = false;
                    switch(spCharacter.toLowerCase()) {
                        case"yes":case"y":
                            wantsSpCharacter = true;
                            break;
                        case"no":case"n":
                            wantsSpCharacter =false;
                            break;
                        default:
                            wantsSpCharacter = false;
                            break;
                    }
                    String pass = g.generatePass(charLowLimit, charLimit, wantsSpCharacter);
                    System.out.println("Here's your password:)");
                    System.out.println(pass);
                    if(newUser != null) {
                        newUser.addAccount("Google",accountName, pass);
                    }
                    saveUser(newUser);

            }
        }
    }
    //Adds the user to a .txt list, so we can search if the user has been added
    public static void addUserToList(String username) {
        try {
            FileWriter fw = new FileWriter("src/AppData/users/userlist.txt", true);
            fw.write(String.format("%s\n", username));
            fw.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
    //Adds a .ser file to the "users" folder, allowing it to be written to later.
    public static User addUser(String user) {
        File f = new File(String.format("src/AppData/users/%s.ser", user.toLowerCase()));
        try {
            FileWriter fw = new FileWriter(f);
            fw.write("");
            fw.close();
        } catch(IOException i) {
            i.getMessage();
        }
        return new User(user);
    }
    public static void saveUser(User user) {
        try{
            File file = new File(String.format("src/AppData/users/%s.ser", user.getUsersName()));
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
            oos.close();
        } catch(Exception fnf) {
            fnf.getMessage();
        }
    }
    public static User loadUser(String username) {
        File userFile = new File(String.format("src/AppData/users/%s.ser", username));
        User user = null;
        try {
            FileInputStream fis = new FileInputStream(userFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            user = (User) ois.readObject();
        } catch(Exception e) {
            e.getMessage();
        }
        return user;
    }

}

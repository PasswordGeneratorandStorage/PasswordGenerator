
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private ArrayList<Account> accounts;
    private String userName;

    public User(String userName) {
        accounts = new ArrayList<>();
        this.userName = userName;
    }

    public void addAccount(String accountName, String username, String pass) {
        accounts.add(new Account(accountName, username, pass));
    }

    public boolean changePass(Account account, String newPass) {
        //If this user has this account, we save. If the account hasn't been added, return false
        if(accounts.contains(account)) {
            //Finds indexOf this account.
            int indexOf = accounts.indexOf(account);
            //Returns account, the calls setPass method with the new password that the user sent in.
            accounts.get(indexOf).setPass(newPass);
            return true;
        } else {
            return false;
        }
    }
    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public String getUsersName() {return userName;}

    public Account getAccount(String accountName) {
        Account account = null;
        for(Account a: accounts) {
            if(a.getUsername().equalsIgnoreCase(accountName)) {
                account = a;
                break;
            }
        }
        return account;
    }
}

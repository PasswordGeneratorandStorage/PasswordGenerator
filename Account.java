

public class Account implements Comparable<Account> {
    private String password;
    private String username;
    private String accountName;

    public Account(String accountName, String username, String password) {
        this.password = password;
        this.username = username;
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


    public String getPass() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public void setPass(String pass) {
        password = pass;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    //Implemented so we can sort them in the User Class.5r
    @Override
    public int compareTo(Account a) {
        return a.getAccountName().compareToIgnoreCase(this.getAccountName());
    }
}

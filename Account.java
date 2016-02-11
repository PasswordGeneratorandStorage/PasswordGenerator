import java.io.Serializable;

public class Account implements Serializable {
    private String password;
    private String account;
    private long timeCreated;

    public Account(String password, String account) {
        this.password = password;
        this.account = account;
        timeCreated = System.currentTimeMillis();
    }
    public String getPass() {
        return password;
    }
    public String getAccount() {
        return account;
    }
    public void setPass(String pass) {
        password = pass;
    }
    public void setAccount(String account) {
        this.account = account;
    }
}

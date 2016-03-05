import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MainGUI extends Application {

    User user;
    Generator generator;
    BorderPane borderPane;
    String currentGenPass;
    UserList userList;

    public MainGUI(User user) {

        userList = new UserList();
        generator = new Generator();
        this.user = user;
        for(Account a: user.getAccounts()) {
            System.out.println(a.getAccountName());
        }
        Stage stage = new Stage();
        stage.setTitle("Welcome " + user.getUsersName());
        borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#ECEFF1"), CornerRadii.EMPTY, Insets.EMPTY)));
        setNavigation();
        setGenerator();
        stage.setMaximized(true);
        stage.setScene(new Scene(borderPane));
        stage.show();
    }
    public void start(Stage primaryStage) throws Exception {
        //Can't use, already used in LoginForm....(Throws exception) Everything goes in the constructor, or in methods called by the constructor.
    }



    public void setGenerator() {
        VBox main = new VBox();
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(100, 100, 100, 0));
        pane.setVgap(20);

        Text genTitle = new Text();
        genTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
        main.getChildren().add(genTitle);


        Text minText = new Text();
        minText.setText("Minimum Character Count");
        minText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
        TextField minBox = new TextField();

        Text maxText = new Text();
        maxText.setText("Maximum Character Count");
        maxText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
        TextField maxBox = new TextField();


        CheckBox genSpecialBox = new CheckBox("Generate special character?");
        genSpecialBox.setFont(Font.font("Tahoma", FontWeight.LIGHT, 25));


        Text numDigitsText = new Text();
        numDigitsText.setText("Number of Digits Desired");
        TextField numDigitsBox = new TextField();

        numDigitsText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));


        Text passDisplay = new Text();
        passDisplay.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 28));
        passDisplay.setWrappingWidth(320);

        Button submit = new Button("Generate");
        submit.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
        submit.setOnAction(event-> {
            try {
                int min = Integer.parseInt(minBox.getText());
                int max = Integer.parseInt(maxBox.getText());
                int numDigits = Integer.parseInt(numDigitsBox.getText());
                boolean genSpecial = genSpecialBox.isSelected();

                currentGenPass = generator.generatePass(min, max, numDigits, genSpecial);
                passDisplay.setText(currentGenPass);
                submit.setText("Re-generate");

            } catch(NumberFormatException nfe) {
                System.out.println("Failed.");
            }
        });

        pane.add(minText, 0, 0);
        pane.add(minBox, 1, 0, 2, 1);

        pane.add(maxText,0,1);
        pane.add(maxBox, 1,1, 2, 1);

        pane.add(numDigitsText,0,2);
        pane.add(numDigitsBox, 1, 2, 2, 1);

        pane.add(genSpecialBox, 0, 3);
        pane.add(submit, 1, 4, 2, 1);

        main.getChildren().add(pane);
        main.getChildren().add(passDisplay);

        borderPane.setRight(main);

    }

    public void setNavigation() {
        VBox accountMenu = new VBox();
        accountMenu.setSpacing(20);
        accountMenu.setPadding(new Insets(30, 0, 0 , 0));
        Text accountsText = new Text();
        accountsText.setText("Accounts");
        accountsText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 27));
        accountsText.setTextAlignment(TextAlignment.CENTER);

        accountMenu.setAlignment(Pos.TOP_CENTER);
        accountMenu.getChildren().add(accountsText);


        //This code is dope.
        for(Account a: user.getAccounts()) {
            Button accountButton = new Button();
            accountButton.setBorder(Border.EMPTY);
            accountButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("#E0E0E0"), CornerRadii.EMPTY, Insets.EMPTY)));
            accountButton.setOpacity(.87);
            accountButton.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
            accountButton.setMaxWidth(Double.MAX_VALUE);
            //Sets the click action to be to call displayAccount, with current account passed in as value.
            accountButton.setOnAction(event -> displayAccount(a));
            accountButton.setText(a.getAccountName());
            accountMenu.getChildren().add(accountButton);
        }

        Button accountButton = new Button();
        accountButton.setBorder(Border.EMPTY);
        accountButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("#E0E0E0"), CornerRadii.EMPTY, Insets.EMPTY)));
        accountButton.setOpacity(.87);
        accountButton.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        accountButton.setMaxWidth(Double.MAX_VALUE);
        //Sets the click action to be to call displayAccount, with current account passed in as value.
        accountButton.setOnAction(event -> displayNewAccount());
        accountButton.setText("Add an Account");
        accountMenu.getChildren().add(accountButton);

        borderPane.setLeft(accountMenu);
    }

    public void displayNewAccount() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(100, 100, 100, 100));
        grid.setVgap(20);
        grid.setHgap(20);

        Text accountName = new Text();
        accountName.setText("Account name:");
        accountName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
        grid.add(accountName, 0, 0);

        TextField accountField = new TextField();
        accountField.setFont(Font.font("tahoma", FontWeight.NORMAL, 25));
        grid.add(accountField, 1, 0);

        Text username = new Text();
        username.setText("User name:");
        username.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
        grid.add(username, 0, 1);

        TextField usernameField = new TextField();
        usernameField.setFont(Font.font("tahoma", FontWeight.NORMAL, 25));
        grid.add(usernameField, 1, 1);

        Text passWord = new Text();
        passWord.setText("Password:");
        passWord.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));

        PasswordField pwField = new PasswordField();
        pwField.setFont(Font.font("tahoma", FontWeight.NORMAL, 25));
        TextField pwShowField = new TextField();
        pwShowField.setFont(Font.font("tahoma", FontWeight.NORMAL, 25));
        CheckBox showPass = new CheckBox();
        showPass.setText("Show password?");
        showPass.setFont(Font.font("Tahoma", FontWeight.LIGHT, 17));

        //Makes the text on both fields the same.

        pwField.textProperty().bindBidirectional(pwShowField.textProperty());

        //When checkbox is selected, pwField isn't shown, but pwShowField is.
        pwField.visibleProperty().bind(showPass.selectedProperty().not());
        pwShowField.visibleProperty().bind(showPass.selectedProperty());

        Button copy = new Button();
        copy.setText("Transfer generated password?");
        copy.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        copy.setOnAction(event-> {
            if(currentGenPass != null) {
                pwShowField.setText(currentGenPass);
                showPass.setSelected(true);
            }
        });

        Button save = new Button();
        save.setText("Save");
        save.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        save.setOnAction(event-> {
            String accountString = accountField.getText();
            String usernameString = usernameField.getText();
            String passwordString = pwField.getText();
            if(passwordString != null && accountString !=null && usernameString != null) {
                user.addAccount(accountString,usernameString,passwordString);
                userList.saveUser(user, user.getPass());
                //Updates with new account.
                setNavigation();
            }
        });



        grid.add(passWord, 0, 2);
        grid.add(pwField, 1, 2);
        grid.add(pwShowField, 1, 2);
        grid.add(showPass, 0, 3);
        grid.add(copy, 0, 4);
        grid.add(save, 0, 5);
        borderPane.setCenter(grid);




    }

    /**
     * Displays accounts, allows for editing of account details.
     * @param account
     */
    public void displayAccount(Account account) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(100,20,100,150));
        gridPane.setVgap(20);
        TextField title = new TextField();
        title.setText(account.getAccountName());
        title.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 30));

        TextField username = new TextField();
        username.setText(account.getUsername());
        username.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        CheckBox showPass = new CheckBox();
        showPass.setText("Show password?");
        showPass.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));

        PasswordField pField = new PasswordField();
        TextField pShowField = new TextField();
        pField.textProperty().bindBidirectional(pShowField.textProperty());

        pField.visibleProperty().bind(showPass.selectedProperty().not());
        pShowField.visibleProperty().bind(showPass.selectedProperty());


        pShowField.setText(account.getPass());
        pShowField.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        pField.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        Button copy = new Button();
        copy.setText("Transfer password to account");
        copy.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        copy.setOnAction(event-> {
            if(currentGenPass != null) {
                pShowField.setText(currentGenPass);
                showPass.setSelected(true);
            }
        });

        Button saveButton = new Button();
        saveButton.setText("Save account");
        saveButton.setFont(Font.font("tahoma", FontWeight.NORMAL, 15));
        saveButton.setOnAction(event-> {
            account.setAccountName(title.getText());
            account.setPass(pShowField.getText());
            account.setUsername(username.getText());
            userList.saveUser(user,user.getPass());
            setNavigation();
        });

        gridPane.add(title, 0,0);
        gridPane.add(username, 0, 1);
        gridPane.add(pShowField, 0, 2);
        gridPane.add(pField, 0, 2);
        gridPane.add(showPass, 0, 3);
        gridPane.add(copy, 0, 4);
        gridPane.add(saveButton, 0, 5);

        borderPane.setCenter(gridPane);


    }
}

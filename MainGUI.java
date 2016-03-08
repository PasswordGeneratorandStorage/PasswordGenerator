import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class MainGUI extends Application {

    Clipboard clipboard;

    User user;
    Generator generator;
    BorderPane borderPane;
    String currentGenPass;
    UserList userList;

    public MainGUI(User user) {

        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        userList = new UserList();
        generator = new Generator();
        this.user = user;
        for(Account a: user.getAccounts()) {
            System.out.println(a.getAccountName());
        }
        Stage stage = new Stage();
        stage.setTitle("Welcome" + user.getUsersName());
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
        genTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 35));
        genTitle.setTextAlignment(TextAlignment.CENTER);
        genTitle.setText("Generator");

        Text minText = new Text();
        minText.setText("Minimum Character Count  ");
        minText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        TextField minBox = new TextField();
        minBox.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        Text maxText = new Text();
        maxText.setText("Maximum Character Count  ");
        maxText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        TextField maxBox = new TextField();
        maxBox.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        Text numDigitsText = new Text();
        numDigitsText.setText("Number of Digits Desired  ");
        TextField numDigitsBox = new TextField();
        numDigitsText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        numDigitsBox.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        CheckBox genSpecialBox = new CheckBox("Generate special character? \n(Will be used as word separator)");
        genSpecialBox.setFont(Font.font("Tahoma", FontWeight.LIGHT, 15));


        Text passDisplay = new Text();
        passDisplay.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 28));
        passDisplay.setWrappingWidth(400);

        Button submit = new Button("Generate");
        submit.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
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
                Text failed = new Text();
                failed.setText("Please make sure all boxes are filled.");
                failed.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
                failed.setFill(Paint.valueOf("#B22222"));
            }
        });

        pane.add(genTitle,0,0,2,1);

        pane.add(minText, 0, 1);
        pane.add(minBox, 1, 1, 2, 1);

        pane.add(maxText,0,2);
        pane.add(maxBox, 1,2, 2, 1);

        pane.add(numDigitsText,0,3);
        pane.add(numDigitsBox, 1, 3, 2, 1);

        pane.add(genSpecialBox, 1, 4, 2, 1);
        pane.add(submit, 1, 5, 2, 1);

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

        Button newAccountButton = new Button();
        newAccountButton.setBorder(Border.EMPTY);
        newAccountButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("#E0E0E0"), CornerRadii.EMPTY, Insets.EMPTY)));
        newAccountButton.setOpacity(.87);
        newAccountButton.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        newAccountButton.setMaxWidth(Double.MAX_VALUE);
        //Sets the click action to be to call displayAccount, with current account passed in as value.
        newAccountButton.setOnAction(event -> displayAddAccountMenu());
        newAccountButton.setText("Add an Account");
        accountMenu.getChildren().add(newAccountButton);


        //This code is dope.
        for(Account a: user.getAccounts()) {
            Button accountButton = new Button();
            accountButton.setBorder(Border.EMPTY);
            accountButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("#E0E0E0"), CornerRadii.EMPTY, Insets.EMPTY)));
            accountButton.setOpacity(.87);
            accountButton.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
            accountButton.setMaxWidth(Double.MAX_VALUE);
            //Sets the click action to be to call displayAccount, with current account passed in as value.
            accountButton.setOnAction(event -> displayAccount(a));
            accountButton.setText(a.getAccountName());
            accountMenu.getChildren().add(accountButton);
        }




        ScrollPane mainScroll = new ScrollPane();
        mainScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        mainScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScroll.setMaxWidth(400);
        mainScroll.setContent(accountMenu);


        borderPane.setLeft(mainScroll);
    }

    public void displayAddAccountMenu() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(100, 100, 100, 100));
        grid.setVgap(20);
        grid.setHgap(20);

        Text accountsText = new Text();
        accountsText.setText("New Account");
        accountsText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 30));
        accountsText.setTextAlignment(TextAlignment.CENTER);

        Text accountName = new Text();
        accountName.setText("Account name:");
        accountName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));


        TextField accountField = new TextField();
        accountField.setFont(Font.font("tahoma", FontWeight.NORMAL, 20));


        Text username = new Text();
        username.setText("User name:");
        username.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));


        TextField usernameField = new TextField();
        usernameField.setFont(Font.font("tahoma", FontWeight.NORMAL, 20));


        Text passWord = new Text();
        passWord.setText("Password:");
        passWord.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        PasswordField pwField = new PasswordField();
        pwField.setFont(Font.font("tahoma", FontWeight.NORMAL, 20));
        TextField pwShowField = new TextField();
        pwShowField.setFont(Font.font("tahoma", FontWeight.NORMAL, 20));
        CheckBox showPass = new CheckBox();
        showPass.setText("Show password?");
        showPass.setFont(Font.font("Tahoma", FontWeight.LIGHT, 15));

        //Makes the text on both fields the same.

        pwField.textProperty().bindBidirectional(pwShowField.textProperty());

        //When checkbox is selected, pwField isn't shown, but pwShowField is.
        pwField.visibleProperty().bind(showPass.selectedProperty().not());
        pwShowField.visibleProperty().bind(showPass.selectedProperty());

        Button transferFromGenerator = new Button();
        transferFromGenerator.setText("Transfer generated password?");
        transferFromGenerator.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        transferFromGenerator.setOnAction(event-> {
            if(currentGenPass != null) {
                pwShowField.setText(currentGenPass);
                showPass.setSelected(true);
            }
        });

        Button save = new Button();
        save.setText("Add Account");
        save.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
        save.setOnAction(event-> {
            String accountString = accountField.getText();
            String usernameString = usernameField.getText();
            String passwordString = pwField.getText();
            if(accountString.length() >0 && usernameString.length() > 0 && passwordString.length() > 0) {
                //Adds account, displays that account.
                displayAccount(user.addAccount(accountString,usernameString,passwordString));

                userList.saveUser(user, user.getPass());

                //Updates navigation with new account.

                setNavigation();
            } else {
                Text text = new Text();
                text.setText("Please fill out all fields.");
                text.setFont(Font.font("tahoma", FontWeight.NORMAL, 20));
                text.setFill(Paint.valueOf("#B22222"));
                grid.add(text,0,6);
            }
        });

        grid.add(accountsText, 0, 0, 2, 1);
        grid.add(accountName, 0, 1);
        grid.add(accountField, 1, 1);
        grid.add(username, 0, 2);
        grid.add(usernameField, 1, 2);


        grid.add(passWord, 0, 3);
        grid.add(pwField, 1, 3);
        grid.add(pwShowField, 1, 3);
        grid.add(showPass, 0, 4);
        grid.add(transferFromGenerator, 0, 5);
        grid.add(save, 1, 5);
        borderPane.setCenter(grid);




    }

    /**
     * Displays accounts, allows for editing of account details.
     * @param account account that gets displayed.
     */
    public void displayAccount(Account account) {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(100,100,100,100));
        gridPane.setVgap(20);
        gridPane.setHgap(20);

        Text genTitle = new Text();
        genTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 35));
        genTitle.setTextAlignment(TextAlignment.CENTER);
        genTitle.setText("Account");


        TextField accountName = new TextField();
        accountName.setText(account.getAccountName());
        accountName.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 20));

        TextField username = new TextField();
        username.setText(account.getUsername());
        username.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Button userNameCopy = new Button();
        userNameCopy.setText("Copy to Clipboard");
        userNameCopy.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        userNameCopy.setOnAction(event-> setClipboard(account.getUsername()));

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

        Button passCopy = new Button();
        passCopy.setText("Copy to Clipboard");
        passCopy.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        passCopy.setOnAction(event-> setClipboard(account.getPass()));

        Button transferFromGen = new Button();
        transferFromGen.setText("Transfer password from Generator");
        transferFromGen.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        transferFromGen.setOnAction(event-> {
            if(currentGenPass != null) {
                pShowField.setText(currentGenPass);
                showPass.setSelected(true);
            }
        });


        Button saveButton = new Button();
        saveButton.setText("Save account");
        saveButton.setFont(Font.font("tahoma", FontWeight.BOLD, 15));
        saveButton.setOnAction(event-> {
            account.setAccountName(accountName.getText());
            account.setPass(pShowField.getText());
            account.setUsername(username.getText());
            userList.saveUser(user,user.getPass());
            setNavigation();
        });


        Button deleteAccount = new Button();
        deleteAccount.setText("Delete Account");
        deleteAccount.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
        deleteAccount.setTextAlignment(TextAlignment.CENTER);
        deleteAccount.setTextFill(Paint.valueOf("#B22222"));
        deleteAccount.setOnAction(event -> {
            deleteAccount.setDisable(true);
            Button confirmDelete = new Button();
            confirmDelete.setText("Click to confirm delete");
            confirmDelete.setFont(Font.font("Tahoma",FontWeight.BOLD, 15));
            confirmDelete.setTextFill(Paint.valueOf("#B22222"));
            confirmDelete.setOnAction(confirmed -> {
                user.deleteAccount(account);
                userList.saveUser(user, user.getPass());
                setNavigation();
                displayAddAccountMenu();
            });
            gridPane.add(confirmDelete,1, 7);

        });

        gridPane.add(genTitle, 0 , 0, 2, 1);
        gridPane.add(accountName, 0,1);
        gridPane.add(username, 0, 2);
        gridPane.add(userNameCopy, 1, 2);
        gridPane.add(pShowField, 0, 3);
        gridPane.add(pField, 0, 3);
        gridPane.add(passCopy, 1, 3);
        gridPane.add(showPass, 0, 4);
        gridPane.add(transferFromGen, 0, 5);
        gridPane.add(saveButton, 1, 5);
        gridPane.add(deleteAccount, 0 ,7, 2, 1);

        borderPane.setCenter(gridPane);


    }

    private void setClipboard(String clip) {
        StringSelection selection = new StringSelection(clip);
        clipboard.setContents(selection, selection);
    }
}

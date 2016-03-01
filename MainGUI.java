import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainGUI extends Application {

    User user;
    Generator generator;
    BorderPane borderPane;

    public MainGUI(User user) {
        generator = new Generator();
        this.user = user;
        Stage stage = new Stage();
        stage.setTitle("Welcome " + user.getUsersName());
        borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#ECEFF1"), CornerRadii.EMPTY, Insets.EMPTY)));
        borderPane.setLeft(getNavigationPane());
        stage.setMaximized(true);
        stage.setScene(new Scene(borderPane, 500, 500));
        stage.show();
    }
    public void start(Stage primaryStage) throws Exception {
        //Can't use, already used in LoginForm....(Throws exception) Everything goes in the constructor, or in methods called by the constructor.
    }

    public VBox getNavigationPane() {
        VBox vbox = new VBox(8);
        vbox.setPadding(new Insets(10));
        vbox.setPrefWidth(200);
        vbox.setFillWidth(true);


        Button accountsButton = new Button();
        accountsButton.setBorder(Border.EMPTY);
        accountsButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("#E0E0E0"), CornerRadii.EMPTY, Insets.EMPTY)));
        accountsButton.setOpacity(.87);
        accountsButton.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        accountsButton.setMaxWidth(Double.MAX_VALUE);
        //Sets the click action to be to call displayAccounts()
        accountsButton.setOnAction(event -> displayAccounts());
        accountsButton.setText("Accounts");
        vbox.getChildren().add(accountsButton);


        Button generatorButton = new Button();
        generatorButton.setBorder(Border.EMPTY);
        generatorButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("#E0E0E0"), CornerRadii.EMPTY, Insets.EMPTY)));
        generatorButton.setOpacity(.87);
        generatorButton.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        generatorButton.setMaxWidth(Double.MAX_VALUE);
        //Sets the click action to be to call displayGenerator()
        generatorButton.setOnAction(event -> displayGenerator());
        generatorButton.setText("Generator");
        vbox.getChildren().add(generatorButton);




        return vbox;
    }


    public void addTabs() {
        TabPane tabPane = new TabPane();

        for(Account account: user.getAccounts()) {
            Tab tmpTab = new Tab(account.getAccountName());
            tmpTab.setContent(new Button(account.getAccountName()));
            tabPane.getTabs().add(tmpTab);
        }
        borderPane.setTop(tabPane);
    }

    public void displayAccounts() {
        GridPane displayBox = new GridPane();
        displayBox.setHgap(20);
        displayBox.setVgap(30);
        int i = 0;
        for(Account account: user.getAccounts()) {
            GridPane accountVBox = new GridPane();
            accountVBox.setVgap(10);
            accountVBox.setHgap(10);
            TextField accountNameDisplay = new TextField(account.getAccountName());
            accountNameDisplay.setOnAction(event -> {
                if(accountNameDisplay.isEditable()) {
                    accountNameDisplay.setEditable(false);
                }
            });

            accountNameDisplay.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 30));
            TextField userNameDisplay = new TextField(account.getUsername());


            userNameDisplay.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            PasswordField passDisplay = new PasswordField();
            passDisplay.setText(account.getPass());
            passDisplay.setEditable(false);

            CheckBox showPass = new CheckBox("Show password?");
            TextField textField = new TextField();
            textField.setManaged(false);
            textField.setVisible(false);


            textField.managedProperty().bind(showPass.selectedProperty());
            textField.visibleProperty().bind(showPass.selectedProperty());
            passDisplay.managedProperty().bind(showPass.selectedProperty().not());
            passDisplay.visibleProperty().bind(showPass.selectedProperty().not());
            textField.textProperty().bindBidirectional(passDisplay.textProperty());


            textField.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            passDisplay.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

            //Adds buttons created to box.
            accountVBox.add(accountNameDisplay, 0 , 1);
            accountVBox.add(userNameDisplay, 0, 2);
            accountVBox.add(textField, 0, 3);
            accountVBox.add(passDisplay, 0, 3);
            accountVBox.add(showPass, 1, 3);

            displayBox.add(accountVBox, 0, i);
            i+=2;
        }
        borderPane.setCenter(displayBox);

    }

    public void displayGenerator() {
        GridPane pane = new GridPane();
        pane.setOpaqueInsets(new Insets(100, 100, 100, 100));
        pane.setHgap(20);
        pane.setVgap(40);
        pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        TextField minBox = new TextField();
        minBox.setPromptText("Minimum Character Count");
        minBox.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        TextField maxBox = new TextField();
        maxBox.setPromptText("Maximum Character Count");

        maxBox.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        CheckBox genSpecialBox = new CheckBox("Generate special character?");

        TextField numDigitsBox = new TextField();
        numDigitsBox.setPromptText("Number of Digits Desired");
        numDigitsBox.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));


        Text passDisplay = new Text();
        passDisplay.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));

        Button submit = new Button("Submit");
        submit.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        submit.setOnAction(event-> {
            try {
                int min = Integer.parseInt(minBox.getText());
                int max = Integer.parseInt(maxBox.getText());
                int numDigits = Integer.parseInt(numDigitsBox.getText());
                boolean genSpecial = genSpecialBox.isSelected();

                String pass = generator.generatePass(min, max, numDigits, genSpecial);
                passDisplay.setText(pass);
                submit.setText("Re-generate");
            } catch(NumberFormatException nfe) {
                System.out.println("Failed.");
            }
        });


        pane.add(minBox, 0, 0, 2, 1);
        pane.add(maxBox, 0,1, 2, 1);
        pane.add(numDigitsBox, 0, 2, 2, 1);
        pane.add(genSpecialBox, 0, 3);
        pane.add(submit, 1, 4, 2, 1);
        pane.add(passDisplay, 0, 6, 5, 1);

        borderPane.setCenter(pane);

    }
}

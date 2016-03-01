import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
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
        borderPane.setLeft(getNavigationPane());
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

    public void displayAccounts() {
        VBox displayBox = new VBox();
        for(Account a: user.getAccounts()) {
            Text accountNameDisplay = new Text(a.getAccountName());
            accountNameDisplay.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 30));
            Text userNameDisplay = new Text(a.getUsername());
            userNameDisplay.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            Text passDisplay = new Text(a.getPass());
            passDisplay.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

            //Adds buttons created to box.
            displayBox.getChildren().add(accountNameDisplay);
            displayBox.getChildren().add(userNameDisplay);
            displayBox.getChildren().add(passDisplay);

        }
        borderPane.setCenter(displayBox);
    }

    public void displayGenerator() {
        GridPane pane = new GridPane();

        TextField minBox = new TextField();
        minBox.setPromptText("Minimum Character Count");

        TextField maxBox = new TextField();
        maxBox.setPromptText("Maximum Character Count");

        CheckBox genSpecialBox = new CheckBox("Generate special character?");

        TextField numDigitsBox = new TextField();
        numDigitsBox.setPromptText("Number of Digits Desired");

        Text passDisplay = new Text();
        passDisplay.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));

        Button submit = new Button("Submit");
        submit.setOnAction(event-> {
            try {
                int min = Integer.parseInt(minBox.getText());
                int max = Integer.parseInt(maxBox.getText());
                int numDigits = Integer.parseInt(numDigitsBox.getText());
                boolean genSpecial = genSpecialBox.isSelected();

                String pass = generator.generatePass(min, max, numDigits, genSpecial);
                passDisplay.setText(pass);
            } catch(NumberFormatException nfe) {
                System.out.println("Failed.");
            }
        });


        pane.add(minBox, 0, 0, 2, 1);
        pane.add(maxBox, 0,1, 2, 1);
        pane.add(numDigitsBox, 0, 2, 2, 1);
        pane.add(genSpecialBox, 0, 3);
        pane.add(submit, 4, 4, 2, 1);
        pane.add(passDisplay, 0, 6, 5, 1);

        borderPane.setCenter(pane);

    }
}

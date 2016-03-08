
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class LoginForm extends Application {


    public static void main(String[] args) {

        launch(args);
    }


    String username;
    Stage primaryStage;
    Text sceneTitle;
    TextField textField;
    Button btn;
    GridPane grid;
    UserList userList;

    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        //Loads list of users, so we can tell who's in the database and not.
        userList = new UserList();


        primaryStage.setTitle("Welcome");
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        sceneTitle = new Text("Welcome.\nPlease enter your name.");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0,0,2,1);

        textField = new TextField();
        textField.setPromptText("Case insensitive");
        grid.add(textField, 0, 1, 2, 1);


        btn = new Button("Next");
        btn.setAlignment(Pos.CENTER_RIGHT);
        grid.add(btn, 2, 2);

        final Text actionTarget = new Text();
        grid.add(actionTarget, 1, 6);
        btn.setOnAction(event -> {
            username = textField.getText();
            if(userList.checkIfUserExists(username)) showExistingUserLogin();
            else showNewUserMenu();

        });




        primaryStage.setScene(new Scene(grid));
        primaryStage.show();
    }

    public void showExistingUserLogin() {

        PasswordField pField = new PasswordField();
        textField.visibleProperty().bind(pField.visibleProperty().not());
        textField.setText("");

        CheckBox showPass = new CheckBox("Show password?");

        textField.managedProperty().bind(showPass.selectedProperty());
        textField.visibleProperty().bind(showPass.selectedProperty());

        pField.managedProperty().bind(showPass.selectedProperty().not());
        pField.visibleProperty().bind(showPass.selectedProperty().not());

        textField.textProperty().bindBidirectional(pField.textProperty());
        pField.textProperty().bindBidirectional(textField.textProperty());

        sceneTitle.setText("Welcome back,\nPlease enter your password");
        btn.setText("Submit");

        btn.setOnAction(event-> {

            if(userList.isCorrectPassword(username, textField.getText())) {
                new MainGUI(userList.loadUser(username,textField.getText()));
                primaryStage.close();
            } else {
                Text error = new Text();
                error.setText("Incorrect Password");
                error.setFill(Color.FIREBRICK);
                error.setFont(Font.font("Tahoma"));
                grid.add(error, 0, 2);
            }
        });


        grid.add(pField, 0, 1, 2, 1);
        grid.add(showPass, 2, 1);


    }

    public void showNewUserMenu() {

        sceneTitle.setText("Looks like you're new,\nplease enter a password.");
        textField.setText("");

        CheckBox showPass = new CheckBox();
        showPass.setText("Show password?");
        showPass.setFont(Font.font("tahoma", FontWeight.NORMAL, 15));

        PasswordField pField1 = new PasswordField();
        textField.visibleProperty().bind(showPass.selectedProperty());
        pField1.promptTextProperty().bindBidirectional(textField.promptTextProperty());
        pField1.visibleProperty().bind(showPass.selectedProperty().not());
        pField1.textProperty().bindBidirectional(textField.textProperty());


        PasswordField pField2 = new PasswordField();
        TextField textField1 = new TextField();
        pField2.textProperty().bindBidirectional(textField1.textProperty());
        pField2.promptTextProperty().bindBidirectional(textField1.promptTextProperty());
        pField2.setPromptText("Confirm password");
        pField2.visibleProperty().bind(showPass.selectedProperty().not());
        textField1.visibleProperty().bind(showPass.selectedProperty());

        Text tip = new Text();
        tip.setText("Write down this password, you cannot login without it.");
        tip.setWrappingWidth(150);
        tip.setFont(Font.font("tahoma", FontWeight.LIGHT, 12));

        grid.add(pField1, 0, 1, 2, 1);
        grid.add(pField2, 0, 2,2,1);
        grid.add(textField1, 0,2,2,1);
        grid.add(showPass,0, 3);
        grid.add(tip,0,4,2,1);

        btn.setText("Submit");
        btn.setOnAction(event ->{
            String pass1 = textField.getText();
            String pass2 = textField1.getText();
            if(pass1.equals(pass2)) {
                //Creates new user
                User user = new User(username, pass1);
                //Saves to file.
                userList.saveUser(user, user.getPass());
                userList.savePassword(user.getUsersName(), user.getPass());
                //Passes new user into the mainGUI.
                new MainGUI(user);
                primaryStage.close();

            } else {
                Text error = new Text();
                error.setText("Passwords don't match.");
                error.setFill(Color.FIREBRICK);
                error.setFont(Font.font("Tahoma"));
                grid.add(error, 1, 3);
            }
        });


    }
}

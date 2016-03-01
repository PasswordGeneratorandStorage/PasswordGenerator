
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginForm extends Application {


    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) throws Exception {
        //Loads list of users, so we can tell who's in the database and not.
        UserList userList = new UserList();

        primaryStage.setTitle("Welcome");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Welcome");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0,0,2,1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userNameField = new TextField();
        grid.add(userNameField, 1, 1);

        Label pw = new Label("Master Password");
        grid.add(pw, 0, 2);

        TextField passwordField = new TextField();
        grid.add(passwordField, 1, 2);

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actionTarget = new Text();
        grid.add(actionTarget, 1, 6);
        btn.setOnAction(event -> {
            //TODO: Create and show MainGUI, passing in loaded User
            //This code is just to test.
            User u = new User("Myles");
            u.addAccount("Google", "Myles", "Password");
            u.addAccount("Outlook", "Username", "Password");
            u.addAccount("reddit", "username", "password");
            primaryStage.close();
            //This code will stay.
            new MainGUI(u);

        });

        primaryStage.setScene(new Scene(grid, 300, 275));
        primaryStage.show();
    }

}

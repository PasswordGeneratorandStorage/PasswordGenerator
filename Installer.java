import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

/**
 * This method houses the installer for the program
 *
 * @author Ian Hoegen
 */
public class Installer extends Application
{
    public static void main(String args[])
    {
        launch(args);
    }

    public String CHOSEN_LOCATION;
    public File selectedDirectory = new File(System.getProperty("user.home"));
    Text filePresenter;

    /**
     * This method launches the GUI for the installer
     *
     * @param primaryStage Required for javaFX
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Text sceneTitle;
        Button btn;
        GridPane grid;
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.setTitle("Install");
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        sceneTitle = new Text("Welcome to the Password Storage and Generator.");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        grid.add(sceneTitle, 0, 0, 2, 1);
        Text action = new Text("Please choose install directory");
        action.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        grid.add(action, 0, 1, 2, 1);


        btn = new Button("Choose directory");
        Button next = new Button("Install");
        btn.setAlignment(Pos.CENTER_LEFT);
        grid.add(btn, 0, 3, 1, 1);
        next.setAlignment(Pos.CENTER_RIGHT);
        grid.add(next, 2, 8);

        filePresenter = new Text(selectedDirectory + "\\PasswordGenerator");
        filePresenter.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        grid.add(filePresenter, 0, 4, 2, 1);
        btn.setOnAction(event -> {
            String USER_HOME = System.getProperty("user.home");
            File dir = new File(USER_HOME);
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Choose Install Location");
            chooser.setInitialDirectory(dir);
            selectedDirectory = chooser.showDialog(primaryStage);
            filePresenter.setText(selectedDirectory.toString() + "\\PasswordGenerator");
        });
        next.setOnAction(event -> {
            install(selectedDirectory);
            primaryStage.close();
            new LoginForm();
        });
        UserList temp = new UserList();
        if (!temp.installed)
        {
            primaryStage.setScene(new Scene(grid));
            primaryStage.show();
        } else
        {
            new LoginForm();
        }
    }

    /**
     * This method installs the word list to the directory
     *
     * @param chosenDirectory: Thr directory to save all data to.
     */
    public void install(File chosenDirectory)
    {

        CHOSEN_LOCATION = chosenDirectory.toString();
        String USER_HOME = System.getProperty("user.home");
        File tempHolder = new File(USER_HOME + "/PasswordGenerator/");
        tempHolder.mkdirs();
        File dir = new File(chosenDirectory.toString() + "/PasswordGenerator/AppData/users/");
        dir.mkdirs();
        String BASE = chosenDirectory.toString();
        InputStream file = getClass().getResourceAsStream("words.txt");
        Scanner readIn;
        readIn = new Scanner(file);
        try
        {
            PrintWriter location = new PrintWriter(new FileWriter(USER_HOME + "/PasswordGenerator/location.ian"));
            location.println(chosenDirectory.toString());
            location.close();
            PrintWriter pw = new PrintWriter(new FileWriter(BASE + "/PasswordGenerator/AppData/words.txt"));
            while (readIn.hasNext())
            {
                pw.println(readIn.nextLine());
            }
        } catch (IOException fnf)
        {
            System.out.println(fnf.getMessage());
        }
    }

}

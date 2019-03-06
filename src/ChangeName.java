import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;

public class ChangeName {

    public static void display(Network network){

        Stage win = new Stage();
        win.initModality(Modality.APPLICATION_MODAL);
        win.setTitle("Change your name");

        HBox login = new HBox(30);
        Label lbLogin = new Label("Your Login now is: " + network.getUsername());
        login.getChildren().addAll(lbLogin);
        login.setAlignment(Pos.CENTER);

        HBox pass = new HBox(10);
        Label lbNewLogin = new Label("New login: ");
        TextField tfNewLogin = new TextField();
        pass.getChildren().addAll(lbNewLogin,tfNewLogin);
        pass.setAlignment(Pos.CENTER);

        HBox buttons = new HBox(50);
        Button buttonChange = new Button("Change it!");
        buttonChange.setOnAction(event -> {

            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try(Connection connection = DriverManager.getConnection("jdbc:sqlite:User_base")) {
                PreparedStatement udateLogin = connection.prepareStatement("UPDATE USERS SET user = ? WHERE user = ?");
                System.out.println(network.getUsername());
                System.out.println(tfNewLogin.getText());
                udateLogin.setString(1,tfNewLogin.getText());
                udateLogin.setString(2,network.getUsername());

                udateLogin.executeUpdate();


        } catch (SQLException e) {
                e.printStackTrace();
            }

            //2 пункт поменять значение в мапе
            try {
                AuthServiceImpl updateLoginMap = new AuthServiceImpl();
                //3 пункт обновить значение в дропбоксе
                Chat.clients.getItems().clear();
                for (String logins: updateLoginMap.users.keySet()) {
                    Chat.clients.getItems().add(logins);
                }
                Chat.clients.getSelectionModel().select(1);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Chat.window.setTitle("Chating...From... " + tfNewLogin.getText()+"!");


            win.close();

            });
        Button buttonCancel = new Button("Cancel");
        buttonCancel.setOnAction(event ->  win.close() );
        buttons.getChildren().addAll(buttonChange,buttonCancel);
        buttons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(5);
        layout.getChildren().addAll(login,pass,buttons);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout,300,300);
        win.setScene(scene);
        win.show();

    }
}

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;


public class Chat extends Application implements sendMsg {
    static Stage window;
AuthServiceImpl auth;
 public static ComboBox<String> clients;
    {
        try {
            auth = new AuthServiceImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Network network;
public TextArea textArea;

    @Override
    public void start (Stage primaryStage) throws Exception {

            network = new Network("127.0.0.1", 8888, this);

        window = primaryStage;

        window.setTitle("Chat");

        HBox sendline = new HBox(2);
        TextField field = new TextField("send message");

        Button buttonSend = new Button("Send");
      clients = new ComboBox<String>();
        for (String logins: auth.users.keySet()) {
            clients.getItems().add(logins);
        }
        clients.getSelectionModel().select(1);
        Button buttonChange = new Button("Change Name");

        sendline.setAlignment(Pos.CENTER);
        sendline.getChildren().addAll(clients,field, buttonSend,buttonChange);

        HBox area = new HBox(5);
        textArea = new TextArea();
        textArea.setEditable(false);
        ScrollPane scroll = new ScrollPane();
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setContent(textArea);
        area.getChildren().addAll(textArea);

        BorderPane bp = new BorderPane();
        bp.setCenter(area);
        bp.setBottom(sendline);

        Scene scene = new Scene(bp, 400, 400);
        window.setScene(scene);

        window.show();

        field.setPrefWidth(scene.getWidth()-buttonSend.getWidth()-150);
        buttonSend.setOnAction(e -> {
            String text = field.getText();
            String userTo = clients.getValue();
            Message msg = new Message(network.getUsername(), userTo, text);
           network.sendMessageToUser(msg);
            field.setText("");

        });
        buttonChange.setOnAction(event -> ChangeName.display(network));

        AuthWindow.display(network);


window.setOnCloseRequest(e-> {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(network.getUsername()+".txt",true))) {
     writer.write( textArea.getText());
    }
    catch (IOException e1) {
    }
    System.exit(0);});
    }

    @Override
    public void sendMsg(Message msg) {
        textArea.appendText(msg.getUserFrom() + "\n" + msg.getText() + "\n");
    }
}

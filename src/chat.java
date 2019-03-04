import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;




public class chat extends Application implements sendMsg {
    static Stage window;
AuthServiceImpl a = new AuthServiceImpl();
public Network network;
public TextArea textArea;
public static String usr;
    @Override
    public void start (Stage primaryStage) throws Exception {

            network = new Network("127.0.0.1", 8888, this);

        window = primaryStage;

        window.setTitle("Chat");

        HBox sendline = new HBox(2);
        TextField field = new TextField("send message");

        Button buttonSend = new Button("Send");
        ComboBox<String> Clients = new ComboBox<String>();
        for (String clients: a.users.keySet()) {
            Clients.getItems().add(clients);
        }
        Clients.getSelectionModel().select(1);

        sendline.setAlignment(Pos.CENTER);
        sendline.getChildren().addAll(Clients,field, buttonSend);

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

        field.setPrefWidth(scene.getWidth()-buttonSend.getWidth()-85);
        buttonSend.setOnAction(e -> {
            String text = field.getText();
            String userTo = Clients.getValue();
            Message msg = new Message(network.getUsername(), userTo, text);
           network.sendMessageToUser(msg);
            field.setText("");

        });

        AuthWindow.display(network);
window.setOnCloseRequest(e-> System.exit(0));
    }

    @Override
    public void sendMsg(Message msg) {
        textArea.appendText(msg.getUserFrom() + "\n" + msg.getText() + "\n");
    }
}

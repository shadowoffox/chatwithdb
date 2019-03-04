import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class AppServer {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AuthServiceImpl forUsers = new AuthServiceImpl();
        Class.forName("org.sqlite.JDBC");
      try(Connection connection = DriverManager.getConnection("jdbc:sqlite:User_base")){
          Statement statement = connection.createStatement();
          ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS WHERE user");
          while (resultSet.next()){
              String key = resultSet.getString("user");
              String value = resultSet.getString("password");
              forUsers.users.put(key,value);
          }
      }
      ChatServer chs =new ChatServer();
      chs.ChatServer();
    }

}

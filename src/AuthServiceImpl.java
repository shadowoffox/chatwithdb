import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class AuthServiceImpl implements AuthService{

public Map<String,String> users = new HashMap<>();


    public AuthServiceImpl() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:User_base")){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS ");
            while (resultSet.next()){
                String key = resultSet.getString("user");
                String value = resultSet.getString("password");
                users.put(key,value);
            }

        }
    }

    @Override
    public boolean AuthUser(String username, String password) {
       String pass = users.get(username);
        System.out.println(users.keySet());
       return pass !=null && pass.equals(password);
    }
}

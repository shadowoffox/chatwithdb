import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AuthServiceImpl implements AuthService{

public Map<String,String> users = new HashMap<>();
    public List<String> onLineUsers = new LinkedList<>();

    public AuthServiceImpl() {
    }

    @Override
    public boolean AuthUser(String username, String password) {
       String pass = users.get(username);
       return pass !=null && pass.equals(password);
    }
}

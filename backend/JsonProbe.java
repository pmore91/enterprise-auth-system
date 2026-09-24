import com.enterprise.auth.JsonUtil;
import java.util.Map;
public class JsonProbe {
  public static void main(String[] args) {
    String json = "{\"username\":\"alice\",\"email\":\"alice@example.com\",\"password\":\"StrongPass123!\",\"confirmPassword\":\"StrongPass123!\"}";
    try {
      Map<String,String> map = JsonUtil.parseObject(json);
      System.out.println("MAP=" + map);
      System.out.println("USERNAME=" + map.get("username"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

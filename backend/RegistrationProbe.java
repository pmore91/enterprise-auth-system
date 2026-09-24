import com.enterprise.auth.*;
public class RegistrationProbe {
  public static void main(String[] args) {
    RegistrationRequest request = new RegistrationRequest();
    request.setUsername("alice");
    request.setEmail("alice@example.com");
    request.setPassword("StrongPass123!");
    request.setConfirmPassword("StrongPass123!");
    ApiResponse response = new RegistrationService(new JdbcUserRepository()).register(request);
    System.out.println(response.getStatus() + ":" + response.getMessage());
  }
}

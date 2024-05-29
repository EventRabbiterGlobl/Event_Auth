package event.com.Event_Auth.config;


import event.com.Event_Auth.entity.User;

public class AuthenticationResponse {


    private String token;

    private final String role;

    private User user;

    public AuthenticationResponse(String token, String role, User user) {
        this.token = token;
        this.role = role;
        this.user = user;
    }


    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }

    public User getUser() {
        return user;
    }
}

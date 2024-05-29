package event.com.Event_Auth.controller;


import event.com.Event_Auth.config.AuthenticationResponse;
import event.com.Event_Auth.dto.OtpData;
import event.com.Event_Auth.entity.User;
import event.com.Event_Auth.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/register")
    public ResponseEntity<User> registry(@RequestBody User user){
        return ResponseEntity.ok( authenticationService.register(user));
    }

    @PostMapping("/otp")
    public ResponseEntity<Boolean> otpVerification(@RequestBody OtpData data){
        System.out.println(data.getOtp());
        return ResponseEntity.ok(authenticationService.otpVerification(data));
    }



    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> longin(@RequestBody User request){
        return ResponseEntity.ok(authenticationService.authenticationResponse(request));
    }







}

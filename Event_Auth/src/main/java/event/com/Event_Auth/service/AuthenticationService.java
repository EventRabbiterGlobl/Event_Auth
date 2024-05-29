package event.com.Event_Auth.service;


import event.com.Event_Auth.config.AuthenticationResponse;
import event.com.Event_Auth.config.JwtService;
import event.com.Event_Auth.dto.OtpData;
import event.com.Event_Auth.dto.SendDataDto;
import event.com.Event_Auth.emailconfig.GenerateOtp;
import event.com.Event_Auth.entity.Role;
import event.com.Event_Auth.entity.User;
import event.com.Event_Auth.feign.EventCreateFeign;
import event.com.Event_Auth.feign.UserFeign;
import event.com.Event_Auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {


    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;


    private final JwtService jwtService;


    private final AuthenticationManager authenticationManager;




    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private GenerateOtp generateOtp;

    @Autowired
    private UserFeign userFeign;


    @Autowired
    private EventCreateFeign eventCreateFeign;



    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }



    public User register(User request){

        Optional<User> userEmail=repository.findByEmail(request.getEmail());

        if (userEmail.isEmpty()){
        User user =new User();
        user.setFirstName(request.getFirstName());
        user.setSecondName(request.getSecondName());
        user.setUsername(request.getUsername());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        String otp=generateOtp.generateOtp();
        user.setOtp(otp);
        sendOtpByEmail(request.getEmail(), otp);

        User userData= repository.save(user);

        SendDataDto sendDataDto= SendDataDto
                .builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .username(user.getUsername())
                .email(user.getEmail())
                .otp(user.getOtp())
                .phoneNumber(user.getPhoneNumber())
                .password(user.getPassword())
                .build();


        userFeign.verificationUser(sendDataDto);
        eventCreateFeign.EventUser(sendDataDto);

        return userData;
        }else {
            return null;
        }
    }




    public boolean otpVerification(OtpData data){
        Optional<User> email=repository.findByEmail(data.getUserEmail());
        if (email.isPresent()){
            User user=email.get();
            if (user.getOtp().equals(data.getOtp())){
                System.out.println(true+"yes true");
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }

    }




    public AuthenticationResponse authenticationResponse(User request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user=repository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.generateToken(user);
         String role = String.valueOf(user.getRole());
        return new AuthenticationResponse(token,role,user);

    }





    private void sendOtpByEmail(String recipientEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Your OTP");
        message.setText("Your OTP is: " + otp);
        javaMailSender.send(message);
    }


}

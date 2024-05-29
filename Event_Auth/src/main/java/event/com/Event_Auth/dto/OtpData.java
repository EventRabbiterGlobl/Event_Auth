package event.com.Event_Auth.dto;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpData {


    String otp;
    String userEmail;
}

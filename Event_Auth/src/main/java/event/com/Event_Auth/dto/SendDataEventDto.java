package event.com.Event_Auth.dto;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendDataEventDto {
    private UUID id;
    private String firstName;
    private String secondName;
    private String username;
    private String phoneNumber;

}

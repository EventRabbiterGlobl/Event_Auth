package event.com.Event_Auth.feign;



import event.com.Event_Auth.dto.SendDataDto;
import event.com.Event_Auth.dto.SendDataEventDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "EVENT-SERVICE")
public interface EventCreateFeign {

    @PostMapping(value = "api/v1/event-create-booking/getUserDataInAuthService",consumes = "application/json")
    ResponseEntity<SendDataEventDto> EventUser(@RequestBody SendDataDto user);

}

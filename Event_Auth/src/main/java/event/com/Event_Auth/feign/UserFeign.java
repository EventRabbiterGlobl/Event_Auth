package event.com.Event_Auth.feign;



import event.com.Event_Auth.dto.SendDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="USER-SERVICE")
public interface UserFeign {

    @PostMapping("api/v1/user/saveProfile")
    ResponseEntity<SendDataDto> verificationUser(@RequestBody SendDataDto user);

}

package mex.edu.utez.siresp.siresp.controller.message;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;


import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-sirep")
@CrossOrigin(origins = {"*"})
public class SmsController {
    @Value("${TWILIO_ACCOUNT_SID}")
    String sid;
    @Value("${TWILIO_AUTH_TOKEN}")
    String token;
    @Value("${TWILIO_NUMBER}")
    String phoneNumber;



    @GetMapping(value = "/sendSMS/")
    public ResponseEntity<String> sendSMS(){
        System.out.println("Si entra aqui?");
        Twilio.init(sid,token);
        Message.creator(new PhoneNumber("+527772599268"),new PhoneNumber(phoneNumber),"¡Hola un nuevo un usuario quiere ser Anfitrión, revisa tu cuenta para verificar...!").create();
        return new ResponseEntity<String>(
                "Message sent Succesfuly", HttpStatus.OK
        );
    }
}
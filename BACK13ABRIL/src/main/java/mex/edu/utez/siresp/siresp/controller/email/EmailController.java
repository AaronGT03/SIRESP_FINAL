package mex.edu.utez.siresp.siresp.controller.email;



import jakarta.validation.Valid;
import mex.edu.utez.siresp.siresp.model.email.Email;
import mex.edu.utez.siresp.siresp.service.email.EmailServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api-sirep/email")
@CrossOrigin(origins = {"*"})
public class EmailController {

    private final EmailServices mail;

    public EmailController(EmailServices mail) {
        this.mail = mail;
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendemail(@Valid @RequestBody Email data,
                                       @Valid BindingResult result) {
        return new ResponseEntity<>(
                mail.sendEmail(data),
                HttpStatus.OK);

    }


}


package mex.edu.utez.siresp.siresp.controller.email;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import mex.edu.utez.siresp.siresp.model.email.Email;
import mex.edu.utez.siresp.siresp.service.email.EmailServices;
import mex.edu.utez.siresp.siresp.utils.ExceptionPersonalizada;
import org.json.JSONObject;
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

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final  String json_data_attribute  = "jsonData";

    public EmailController(EmailServices mail) {
        this.mail = mail;
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendemail(HttpServletRequest request) throws ExceptionPersonalizada, JsonProcessingException {


        JSONObject jsonObject2 = (JSONObject) request.getAttribute(json_data_attribute);
        Email email = objectMapper.readValue(jsonObject2.toString(), Email.class);


        return new ResponseEntity<>(
                mail.sendEmail(email),
                HttpStatus.OK);

    }
}


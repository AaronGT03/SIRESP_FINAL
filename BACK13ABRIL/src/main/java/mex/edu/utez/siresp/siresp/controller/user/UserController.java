package mex.edu.utez.siresp.siresp.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import mex.edu.utez.siresp.siresp.controller.user.dto.UserDto;
import mex.edu.utez.siresp.siresp.model.user.User;
import mex.edu.utez.siresp.siresp.service.user.StorageService;
import mex.edu.utez.siresp.siresp.service.user.UserService;
import mex.edu.utez.siresp.siresp.utils.Encrypt;
import mex.edu.utez.siresp.siresp.utils.ExceptionPersonalizada;
import mex.edu.utez.siresp.siresp.utils.Response;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-sirep/user")
@CrossOrigin(origins = {"*"})
public class UserController {

    private final UserService service;
    private  final StorageService imageService;

    private static final  String json_data_attribute  = "jsonData";

    private final  PasswordEncoder passwordEncoder;

    public UserController(UserService service, StorageService imageService, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.imageService = imageService;
        this.passwordEncoder = passwordEncoder;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();


    @PostMapping("/upload")
    public String upload(@RequestParam("profilePicture") MultipartFile multipartFile) {
        return imageService.upload(multipartFile);
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<String> getAll(@PathVariable String username) throws Exception {
        Response<Optional<User>> usersResponse = this.service.getByUsername(username);

        Optional<User> optionalUser = usersResponse.getData();
        User user = optionalUser.orElse(null);

        String json = objectMapper.writeValueAsString(user);

        String dataEncrypted = Encrypt.encrypt(json);
        return new ResponseEntity<>(
                dataEncrypted,
                HttpStatus.OK
        );
    }
    @GetMapping("/")
    public ResponseEntity<String> getAll() throws Exception {
        Response<List<User>> usersResponse = this.service.getall();
        String json = objectMapper.writeValueAsString(usersResponse);
        String dataEncrypted = Encrypt.encrypt(json);
        return new ResponseEntity<>(
                dataEncrypted,
                HttpStatus.OK
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> getOne(@PathVariable Long id) throws Exception {
        Response<User> userResponse = this.service.getOne(id);
        String json = objectMapper.writeValueAsString(userResponse);
        String dataEncrypted = Encrypt.encrypt(json);
        return new ResponseEntity<>(
                dataEncrypted,
                HttpStatus.OK
        );
    }
    @PostMapping("/actualizar/status")
    public  ResponseEntity<Response<Boolean>> updateStatus(HttpServletRequest request)  {

        JSONObject jsonObject2 = (JSONObject) request.getAttribute(json_data_attribute);
        Long id = Long.valueOf(jsonObject2.getString("id"));
        String status = jsonObject2.getString("status");


        return new ResponseEntity<>(
                this.service.updateStatus(id,status),
                HttpStatus.OK
        );

    }

    @PostMapping("/actualizar/rol")
    public  ResponseEntity<Response<Boolean>> updaterole(HttpServletRequest request) throws ExceptionPersonalizada {

        JSONObject jsonObject2 = (JSONObject) request.getAttribute(json_data_attribute);
        Long id = Long.valueOf(jsonObject2.getString("id"));
        String role = jsonObject2.getString("role");

        return new ResponseEntity<>(
                this.service.updateRole(id,role),
                HttpStatus.OK
        );


    }
    @PostMapping("/actualizar/")
    public  ResponseEntity<Response<User>> update(HttpServletRequest request) throws JsonProcessingException {

            JSONObject jsonObject2 = (JSONObject) request.getAttribute(json_data_attribute);
            UserDto user = objectMapper.readValue(jsonObject2.toString(), UserDto.class);


            return new ResponseEntity<>(
                    this.service.update(user.castTocast()),
                    HttpStatus.OK
            );



    }
    @PostMapping("/")
    public  ResponseEntity<Response<User>> insert(HttpServletRequest request) throws ExceptionPersonalizada {
        try{
            JSONObject jsonObject2 = (JSONObject) request.getAttribute(json_data_attribute);
            UserDto user = objectMapper.readValue(jsonObject2.toString(), UserDto.class);

            String passwordBCrypt = passwordEncoder.encode(user.getPassword());
            user.setPassword(passwordBCrypt);

            return new ResponseEntity<>(
                    this.service.insert(user.castTocast()),
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw new ExceptionPersonalizada("Error inserting data",e);

        }


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> delete(@Valid @PathVariable Long id){
        return new ResponseEntity<>(
                this.service.delete(id),
                HttpStatus.OK
        );
    }


}

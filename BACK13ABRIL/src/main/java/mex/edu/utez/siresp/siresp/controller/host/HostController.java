package mex.edu.utez.siresp.siresp.controller.host;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import mex.edu.utez.siresp.siresp.controller.host.dto.HostDto;
import mex.edu.utez.siresp.siresp.model.host.Host;
import mex.edu.utez.siresp.siresp.service.host.HostService;
import mex.edu.utez.siresp.siresp.service.user.StorageService;
import mex.edu.utez.siresp.siresp.utils.Encrypt;
import mex.edu.utez.siresp.siresp.utils.ExceptionPersonalizada;
import mex.edu.utez.siresp.siresp.utils.Response;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api-sirep/host")
@CrossOrigin(origins = {"*"})
public class HostController {

    private final HostService service;

    private final StorageService imageService;

    public HostController(HostService service, StorageService imageService) {
        this.service = service;
        this.imageService = imageService;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();


    @PostMapping("/upload")
    public String upload(@RequestParam("profilePicture") MultipartFile multipartFile) {
        return imageService.upload(multipartFile);
    }

    @GetMapping("/")
    public ResponseEntity<String> getAll() throws Exception {
        Response<List<Host>> response = this.service.getall();
        String json = objectMapper.writeValueAsString(response);


        String dataEncrypted = Encrypt.encrypt(json);

        return new ResponseEntity<>(
                dataEncrypted,
                HttpStatus.OK
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<String> getOne(@PathVariable Long id) throws Exception {
        Response<Host> response = this.service.getOne(id);
        String json = objectMapper.writeValueAsString(response);
        String dataEncrypted = Encrypt.encrypt(json);

        return new ResponseEntity<>(
                dataEncrypted,
                HttpStatus.OK
        );
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<String> getOneByUser(@PathVariable Long id) throws Exception {
        Response<Host> response = this.service.getallByIduser(id);
        System.out.println(response);

        String json = objectMapper.writeValueAsString(response);
        String dataEncrypted = Encrypt.encrypt(json);

        return new ResponseEntity<>(
                dataEncrypted,
                HttpStatus.OK
        );
    }

    @PostMapping("/actualizar/")
    public  ResponseEntity<Response<Host>> update(HttpServletRequest request) throws ExceptionPersonalizada {

        try{
            JSONObject jsonObject2 = (JSONObject) request.getAttribute("jsonData");
            HostDto host = objectMapper.readValue(jsonObject2.toString(), HostDto.class);

            return new ResponseEntity<>(
                    this.service.update(host.casToCast()),
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw new ExceptionPersonalizada("Error updating data",e);

        }

    }
    @PostMapping("/")
    public  ResponseEntity<Response<Host>> insert(HttpServletRequest request) throws ExceptionPersonalizada {

        try{
            JSONObject jsonObject2 = (JSONObject) request.getAttribute("jsonData");
            HostDto host = objectMapper.readValue(jsonObject2.toString(), HostDto.class);

            return new ResponseEntity<>(
                    this.service.insert(host.casToCast()),
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw new ExceptionPersonalizada("Error iserting data",e);

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

package mex.edu.utez.siresp.siresp.controller.cancellation;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import mex.edu.utez.siresp.siresp.controller.cancellation.dto.CancellationDto;
import mex.edu.utez.siresp.siresp.model.cancellation.Cancellation;
import mex.edu.utez.siresp.siresp.service.cancellation.CancellationSerivice;
import mex.edu.utez.siresp.siresp.utils.Encrypt;
import mex.edu.utez.siresp.siresp.utils.ExceptionPersonalizada;
import mex.edu.utez.siresp.siresp.utils.Response;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api-sirep/cancellation")
@CrossOrigin(origins = {"*"})
public class CancellationController {

    private final CancellationSerivice service;

    public CancellationController(CancellationSerivice service) {
        this.service = service;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/")
    public ResponseEntity<String> getAll() throws Exception {
        Response<List<Cancellation>> listResponse = this.service.getAll();
        String json = objectMapper.writeValueAsString(listResponse);
        String dataEncrypted = Encrypt.encrypt(json);
        return new ResponseEntity<>(
                dataEncrypted,
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOne(@PathVariable Long id) throws Exception {
        Response<Cancellation> response = this.service.getOne(id);
        String json = objectMapper.writeValueAsString(response);
        String dataEncrypted = Encrypt.encrypt(json);
        return new ResponseEntity<>(
                dataEncrypted,
                HttpStatus.OK
        );
    }

    @PostMapping("/actualizar/")
    public  ResponseEntity<Response<Cancellation>> update(HttpServletRequest request) throws ExceptionPersonalizada {

        try{
            JSONObject jsonObject2 = (JSONObject) request.getAttribute("jsonData");
            CancellationDto cancellation = objectMapper.readValue(jsonObject2.toString(), CancellationDto.class);

            return new ResponseEntity<>(
                    this.service.update(cancellation.castToCast()),
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw new ExceptionPersonalizada("Error iserting data",e);

        }


    }

    @PostMapping("/")
    public  ResponseEntity<Response<Cancellation>> insert(HttpServletRequest request) throws ExceptionPersonalizada {

        try{
            JSONObject jsonObject2 = (JSONObject) request.getAttribute("jsonData");
            CancellationDto cancellation = objectMapper.readValue(jsonObject2.toString(), CancellationDto.class);
            System.out.println("datos de la cancelacion:"+ cancellation);
            return new ResponseEntity<>(
                    this.service.insert(cancellation.castToCast()),
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

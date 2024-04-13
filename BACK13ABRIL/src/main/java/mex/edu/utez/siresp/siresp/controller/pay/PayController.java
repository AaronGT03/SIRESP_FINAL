package mex.edu.utez.siresp.siresp.controller.pay;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import mex.edu.utez.siresp.siresp.controller.pay.dto.PayDto;

import mex.edu.utez.siresp.siresp.model.pay.Pay;
import mex.edu.utez.siresp.siresp.service.pay.PayService;
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
@RequestMapping("/api-sirep/pay")
@CrossOrigin(origins = {"*"})
public class PayController {

    private final PayService service;

    public PayController(PayService service) {
        this.service = service;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();


    @GetMapping("/")
    public ResponseEntity<String> getAll() throws Exception {
        Response<List<Pay>> listResponse = this.service.getAll();
        String json = objectMapper.writeValueAsString(listResponse);
        String dataEncrypted = Encrypt.encrypt(json);
        return new ResponseEntity<>(
                dataEncrypted,
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOne(@PathVariable Long id) throws Exception {
        Response<Pay> response = this.service.getOne(id);
        String json = objectMapper.writeValueAsString(response);
        String dataEncrypted = Encrypt.encrypt(json);
        return new ResponseEntity<>(
                dataEncrypted,
                HttpStatus.OK
        );
    }

    @PostMapping("/actualizar/")
    public  ResponseEntity<Response<Pay>> update(HttpServletRequest request) throws ExceptionPersonalizada {

        try{
            JSONObject jsonObject2 = (JSONObject) request.getAttribute("jsonData");
            PayDto pay = objectMapper.readValue(jsonObject2.toString(), PayDto.class);

            return new ResponseEntity<>(
                    this.service.update(pay.castToCast()),
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw new ExceptionPersonalizada("Error updating data",e);

        }


    }

    @PostMapping("/")
    public  ResponseEntity<Response<Pay>> insert(HttpServletRequest request) throws ExceptionPersonalizada {
        try{
            JSONObject jsonObject2 = (JSONObject) request.getAttribute("jsonData");
            PayDto pay = objectMapper.readValue(jsonObject2.toString(), PayDto.class);

            return new ResponseEntity<>(
                    this.service.insert(pay.castToCast()),
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

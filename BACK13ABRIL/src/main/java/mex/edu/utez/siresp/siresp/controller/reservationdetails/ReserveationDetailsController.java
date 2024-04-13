package mex.edu.utez.siresp.siresp.controller.reservationdetails;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import mex.edu.utez.siresp.siresp.controller.reservationdetails.dto.ReservationDetailsDto;
import mex.edu.utez.siresp.siresp.model.reservationdetails.ReservationDetails;
import mex.edu.utez.siresp.siresp.service.reservationdetails.ReservationDetailsService;
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
@RequestMapping("/api-sirep/detail")
@CrossOrigin(origins = {"*"})
public class ReserveationDetailsController {

    private  final ReservationDetailsService service;

    public ReserveationDetailsController(ReservationDetailsService service) {
        this.service = service;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/")
    public ResponseEntity<String> getAll() throws Exception {
        Response<List<ReservationDetails>> listResponse = this.service.getAll();
        String json = objectMapper.writeValueAsString(listResponse);
        String dataEncrypted = Encrypt.encrypt(json);
        return new ResponseEntity<>(
                dataEncrypted,
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOne(@PathVariable Long id) throws Exception {
        Response<ReservationDetails> response = this.service.getOne(id);
        String json = objectMapper.writeValueAsString(response);
        String dataEncrypted = Encrypt.encrypt(json);
        return new ResponseEntity<>(
                dataEncrypted,
                HttpStatus.OK
        );
    }
    @PostMapping("/actualizar/")
    public  ResponseEntity<Response<ReservationDetails>> update(HttpServletRequest request) throws ExceptionPersonalizada {

        try{
            JSONObject jsonObject2 = (JSONObject) request.getAttribute("jsonData");
            ReservationDetailsDto reservation = objectMapper.readValue(jsonObject2.toString(), ReservationDetailsDto.class);

            return new ResponseEntity<>(
                    this.service.update(reservation.castTocast()),
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw new ExceptionPersonalizada("Error updating data",e);

        }


    }

    @PostMapping("/")
    public  ResponseEntity<Response<ReservationDetails>> insert(HttpServletRequest request) throws ExceptionPersonalizada {

        try{
            JSONObject jsonObject2 = (JSONObject) request.getAttribute("jsonData");
            ReservationDetailsDto reservation = objectMapper.readValue(jsonObject2.toString(), ReservationDetailsDto.class);

            return new ResponseEntity<>(
                    this.service.insert(reservation.castTocast()),
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw new ExceptionPersonalizada("Error inserting data",e);

        }


    }


}

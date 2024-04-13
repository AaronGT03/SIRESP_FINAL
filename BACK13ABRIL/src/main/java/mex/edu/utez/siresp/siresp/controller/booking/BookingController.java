package mex.edu.utez.siresp.siresp.controller.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import mex.edu.utez.siresp.siresp.controller.booking.dto.BookingDto;

import mex.edu.utez.siresp.siresp.model.booking.Booking;

import mex.edu.utez.siresp.siresp.service.booking.BookingService;
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
@RequestMapping("/api-sirep/booking")
@CrossOrigin(origins = {"*"})
public class BookingController {

    private final  BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();


    @GetMapping("/")
    public ResponseEntity<String> getAll() throws Exception {
        Response<List<Booking>> usersResponse = this.service.getAll();
        String json = objectMapper.writeValueAsString(usersResponse);
        String encryptedhosts = Encrypt.encrypt(json);
        return new ResponseEntity<>(
                encryptedhosts,
                HttpStatus.OK
        );
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<String> getAllById(@PathVariable Long id) throws Exception {
        Response<List<Booking>> usersResponse = this.service.getAllByIdUser(id);
        String json = objectMapper.writeValueAsString(usersResponse);
        String encryptedhosts = Encrypt.encrypt(json);

        return new ResponseEntity<>(
                encryptedhosts,
                HttpStatus.OK
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<String> getOne(@PathVariable Long id) throws Exception {
        Response<Booking> hostResponse = this.service.getOne(id);
        String json = objectMapper.writeValueAsString(hostResponse);
        String dataEncrypted = Encrypt.encrypt(json);
        return new ResponseEntity<>(
                dataEncrypted,
                HttpStatus.OK
        );
    }

    @PostMapping("/actualizar/state/")
    public  ResponseEntity<Response<Boolean>> updateStateById(HttpServletRequest request)  {

        JSONObject jsonObject2 = (JSONObject) request.getAttribute("jsonData");
        Long id = Long.valueOf(jsonObject2.getString("id"));
        String state = jsonObject2.getString("state");;

        return new ResponseEntity<>(
                this.service.updateStateById(state,id),
                HttpStatus.OK);


    }






    @PostMapping("/actualizar/")
    public  ResponseEntity<Response<Booking>> update(HttpServletRequest request) throws ExceptionPersonalizada {
        try {
            JSONObject jsonObject2 = (JSONObject) request.getAttribute("jsonData");
            BookingDto booking = objectMapper.readValue(jsonObject2.toString(), BookingDto.class);

            return new ResponseEntity<>(
                    this.service.update(booking.castToCast()),
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw new ExceptionPersonalizada("Erro updating data", e);
        }


    }

    @PostMapping("/")
    public  ResponseEntity<Response<Booking>> insert(HttpServletRequest request) throws ExceptionPersonalizada {

        try{
            JSONObject jsonObject2 = (JSONObject) request.getAttribute("jsonData");
            BookingDto booking = objectMapper.readValue(jsonObject2.toString(), BookingDto.class);

            return new ResponseEntity<>(
                    this.service.insert(booking.castToCast()),
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

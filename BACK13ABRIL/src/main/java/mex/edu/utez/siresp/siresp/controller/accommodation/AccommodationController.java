package mex.edu.utez.siresp.siresp.controller.accommodation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import mex.edu.utez.siresp.siresp.controller.accommodation.dto.AccommodationDto;
import mex.edu.utez.siresp.siresp.model.accommodation.Accommodation;
import mex.edu.utez.siresp.siresp.service.accommodation.AccommodationService;
import mex.edu.utez.siresp.siresp.service.user.StorageService;
import mex.edu.utez.siresp.siresp.utils.Encrypt;
import mex.edu.utez.siresp.siresp.utils.ExceptionPersonalizada;
import mex.edu.utez.siresp.siresp.utils.InsertionException;
import mex.edu.utez.siresp.siresp.utils.Response;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api-sirep/accommodation")
@CrossOrigin(origins = {"*"})
public class AccommodationController {

    private final   AccommodationService service;

    private final StorageService imageService;


    public AccommodationController(AccommodationService service, StorageService imageService) {
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

        try{
            Response<List<Accommodation>> accommodationResponse = this.service.getall();

            String json2 = objectMapper.writeValueAsString(accommodationResponse);


            String encryptedAccomodations = Encrypt.encrypt(json2);

            return new ResponseEntity<>(
                    encryptedAccomodations,
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw new ExceptionPersonalizada("Erro getting data",e);
        }



    }

    @GetMapping("/category/{id}")
    public ResponseEntity<String> getAllByCategory(@PathVariable Long id) throws Exception {

        try {
            Response<List<Accommodation>> accommodationResponse = this.service.getallByCategory(id);
            String json2 = objectMapper.writeValueAsString(accommodationResponse);


            String encryptedAccomodations = Encrypt.encrypt(json2);

            return new ResponseEntity<>(
                    encryptedAccomodations,
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw new ExceptionPersonalizada("Error getting data by Category_Id",e);
        }

    }


@GetMapping("/numGuest/{num}")
public ResponseEntity<String> getAllByNumguest(@PathVariable int num) throws Exception {

        try {
            Response<List<Accommodation>> accommodationResponse = this.service.getallByNumGuest(num);
            String json2 = objectMapper.writeValueAsString(accommodationResponse);

            String encryptedAccomodations = Encrypt.encrypt(json2);

            return new ResponseEntity<>(
                    encryptedAccomodations,
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw  new ExceptionPersonalizada("Error when searghing by number of guests",e);
        }

}

    @GetMapping("/price/{price}")
    public ResponseEntity<String> getAllByPrice(@PathVariable double price) throws Exception {

        try {
            Response<List<Accommodation>> accommodationResponse = this.service.getallByPrice(price);
            String json2 = objectMapper.writeValueAsString(accommodationResponse);

            String encryptedAccomodations = Encrypt.encrypt(json2);

            return new ResponseEntity<>(
                    encryptedAccomodations,
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw new ExceptionPersonalizada("Error getting data by Price",e);
        }

    }
    @GetMapping("/host/{id}")
    public ResponseEntity<String> getAllByHost(@PathVariable Long id) throws Exception {

        try {
            Response<List<Accommodation>> accommodationResponse = this.service.getallByHost(id);
            String json2 = objectMapper.writeValueAsString(accommodationResponse);

            String encryptedAccomodations = Encrypt.encrypt(json2);

            return new ResponseEntity<>(
                    encryptedAccomodations,
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw new ExceptionPersonalizada("Error getting data by Price",e);
        }

    }
    @GetMapping("/location/{location}")
    public ResponseEntity<String> getAllByPrice(@PathVariable String location) throws Exception {

        try {
            Response<List<Accommodation>> accommodationResponse = this.service.getallByLocation(location);
            String json2 = objectMapper.writeValueAsString(accommodationResponse);

            String encryptedAccomodations = Encrypt.encrypt(json2);

            return new ResponseEntity<>(
                    encryptedAccomodations,
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw new ExceptionPersonalizada("Error getting data by Location",e);
        }

    }



    @GetMapping("/{id}")
    public ResponseEntity<String> getOne(@PathVariable Long id) throws Exception {

        try {
            Response<Accommodation> accommodationResponse = this.service.getOne(id);
            String json = objectMapper.writeValueAsString(accommodationResponse);

            String encryptedAccomodation = Encrypt.encrypt(json);
            return new ResponseEntity<>(
                    encryptedAccomodation,
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw new ExceptionPersonalizada("Error getting data by Id",e);
        }

    }



    @PostMapping("/actualizar/")
    public  ResponseEntity<Response<Accommodation>> update(HttpServletRequest request) throws ExceptionPersonalizada {

        try{
            JSONObject jsonObject2 = (JSONObject) request.getAttribute("jsonData");
            AccommodationDto accommodationDto = objectMapper.readValue(jsonObject2.toString(), AccommodationDto.class);



            return new ResponseEntity<>(
                    this.service.update(accommodationDto.castToCast()),
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw  new ExceptionPersonalizada("Error al actualizar la información",e);
        }


    }
    @PostMapping("/")
    public  ResponseEntity<Response<Accommodation>> insert(HttpServletRequest request) throws ExceptionPersonalizada{

        try {
            JSONObject jsonObject2 = (JSONObject) request.getAttribute("jsonData");
            AccommodationDto accommodationDto = objectMapper.readValue(jsonObject2.toString(), AccommodationDto.class);
            return new ResponseEntity<>(
                    this.service.insert(accommodationDto.castToCast()),
                    HttpStatus.OK
            );

        } catch (IOException e) {
            throw new ExceptionPersonalizada("Error ",e);
        }

    }
    @PostMapping("/status/")
    public  ResponseEntity<Response<Boolean>> updateStatusById(HttpServletRequest request) throws ExceptionPersonalizada{


            JSONObject jsonObject2 = (JSONObject) request.getAttribute("jsonData");
            Long id = Long.valueOf(jsonObject2.getString("id"));
            String status = jsonObject2.getString("status");
            return new ResponseEntity<>(
                    this.service.updateStatusById(status,id),
                    HttpStatus.OK
            );



    }


}

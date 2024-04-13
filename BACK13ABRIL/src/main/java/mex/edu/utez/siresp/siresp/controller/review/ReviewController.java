package mex.edu.utez.siresp.siresp.controller.review;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import mex.edu.utez.siresp.siresp.controller.review.dto.ReviewDto;
import mex.edu.utez.siresp.siresp.model.review.Review;
import mex.edu.utez.siresp.siresp.service.review.ReviewService;
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
@RequestMapping("/api-sirep/review")
@CrossOrigin(origins = {"*"})
public class ReviewController {


    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();


    @GetMapping("/")
    public ResponseEntity<String> getAll() throws Exception {
        Response<List<Review>> listResponse = this.service.getAll();
        String json = objectMapper.writeValueAsString(listResponse);
        String dataEncrypted = Encrypt.encrypt(json);
        return new ResponseEntity<>(
                dataEncrypted,
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOne(@PathVariable Long id) throws Exception {
        Response<Review> response = this.service.getOne(id);
        String json = objectMapper.writeValueAsString(response);
        String dataEncrypted = Encrypt.encrypt(json);
        return new ResponseEntity<>(
                dataEncrypted,
                HttpStatus.OK
        );
    }
    @PostMapping("/actualizar/")
    public  ResponseEntity<Response<Review>> update(HttpServletRequest request) throws ExceptionPersonalizada {

        try{
            JSONObject jsonObject2 = (JSONObject) request.getAttribute("jsonData");
            ReviewDto review = objectMapper.readValue(jsonObject2.toString(), ReviewDto.class);

            return new ResponseEntity<>(
                    this.service.update(review.castToCast()),
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw new ExceptionPersonalizada("Error updating data",e);

        }


    }

    @PostMapping("/")
    public  ResponseEntity<Response<Review>> insert(HttpServletRequest request) throws ExceptionPersonalizada {

        try{
            JSONObject jsonObject2 = (JSONObject) request.getAttribute("jsonData");
            ReviewDto review = objectMapper.readValue(jsonObject2.toString(), ReviewDto.class);

            return new ResponseEntity<>(
                    this.service.insert(review.castToCast()),
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw new ExceptionPersonalizada("Error inserting data",e);

        }


    }

}

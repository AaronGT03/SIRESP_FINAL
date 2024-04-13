package mex.edu.utez.siresp.siresp.controller.activityhistory;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import mex.edu.utez.siresp.siresp.controller.activityhistory.dto.ActivityHistoryDto;
import mex.edu.utez.siresp.siresp.model.activityhistory.ActivityHistory;
import mex.edu.utez.siresp.siresp.service.activityhistory.ActivityHistoryService;
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
@RequestMapping("/api-sirep/activity")
@CrossOrigin(origins = {"*"})
public class ActivityHistoryController {

    private final ActivityHistoryService service;

    public ActivityHistoryController(ActivityHistoryService service) {
        this.service = service;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/")
    public ResponseEntity<String> getAll() throws Exception {

        try {
            Response<List<ActivityHistory>> listResponse = this.service.getAll();
            String json = objectMapper.writeValueAsString(listResponse);
            String dataEncrypted = Encrypt.encrypt(json);
            return new ResponseEntity<>(
                    dataEncrypted,
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw new ExceptionPersonalizada("Error getting data by Id",e);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOne(@PathVariable Long id) throws Exception {

        try {
            Response<ActivityHistory> response = this.service.getOne(id);
            String json = objectMapper.writeValueAsString(response);
            String dataEncrypted = Encrypt.encrypt(json);
            return new ResponseEntity<>(
                    dataEncrypted,
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw new ExceptionPersonalizada("Error getting data by Id",e);
        }

    }


    @PostMapping("/actualizar/")
    public  ResponseEntity<Response<ActivityHistory>> update(HttpServletRequest request) throws ExceptionPersonalizada{

        try {
            JSONObject jsonObject2 = (JSONObject) request.getAttribute("jsonData");
            ActivityHistoryDto  activity = objectMapper.readValue(jsonObject2.toString(), ActivityHistoryDto.class);

            return new ResponseEntity<>(
                    this.service.update(activity.casToCast()),
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw new ExceptionPersonalizada("Error updating data",e);
        }

    }

    @PostMapping("/")
    public  ResponseEntity<Response<ActivityHistory>> insert(HttpServletRequest request) throws  ExceptionPersonalizada {

       try{
           JSONObject jsonObject2 = (JSONObject) request.getAttribute("jsonData");
           ActivityHistoryDto activity = objectMapper.readValue(jsonObject2.toString(), ActivityHistoryDto.class);
           return new ResponseEntity<>(
                   this.service.insert(activity.casToCast()),
                   HttpStatus.OK
           );
       }catch (IOException e){
           throw  new ExceptionPersonalizada("Error inserting data",e);

       }
    }




}

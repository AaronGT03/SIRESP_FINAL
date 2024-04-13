package mex.edu.utez.siresp.siresp.controller.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import mex.edu.utez.siresp.siresp.controller.category.dto.CategoryDto;
import mex.edu.utez.siresp.siresp.model.category.Category;
import mex.edu.utez.siresp.siresp.service.category.CategoryService;
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
@RequestMapping("/api-sirep/category")
@CrossOrigin(origins = {"*"})
public class CategoryController {

    private  final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/")
    public ResponseEntity<String> getAll() throws Exception {
        Response<List<Category>> listResponse = this.service.getAll();
        String json = objectMapper.writeValueAsString(listResponse);
        String dataEncrypted = Encrypt.encrypt(json);
        return new ResponseEntity<>(
                dataEncrypted,
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOne(@PathVariable Long id) throws Exception {
        Response<Category> response = this.service.getOne(id);
        String json = objectMapper.writeValueAsString(response);
        String dataEncrypted = Encrypt.encrypt(json);
        return new ResponseEntity<>(
                dataEncrypted,
                HttpStatus.OK
        );
    }

    @PostMapping("/actualizar/")
    public  ResponseEntity<Response<Category>> update(HttpServletRequest request) throws ExceptionPersonalizada {

        try{
            JSONObject jsonObject2 = (JSONObject) request.getAttribute("jsonData");
            CategoryDto categry = objectMapper.readValue(jsonObject2.toString(), CategoryDto.class);

            return new ResponseEntity<>(
                    this.service.update(categry.castToCast()),
                    HttpStatus.OK
            );
        }catch (IOException e){
            throw new ExceptionPersonalizada("Error updating data",e);

        }

    }

    @PostMapping("/")
    public  ResponseEntity<Response<Category>> insert(HttpServletRequest request) throws ExceptionPersonalizada {

        try{
            JSONObject jsonObject2 = (JSONObject) request.getAttribute("jsonData");
            CategoryDto category = objectMapper.readValue(jsonObject2.toString(), CategoryDto.class);

            return new ResponseEntity<>(
                    this.service.insert(category.castToCast()),
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

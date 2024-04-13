package mex.edu.utez.siresp.siresp.service.category;

import mex.edu.utez.siresp.siresp.model.category.Category;
import mex.edu.utez.siresp.siresp.model.category.CategoryRepository;
import mex.edu.utez.siresp.siresp.utils.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Response<List<Category>> getAll(){
        return  new Response<>(
                this.repository.findAll(),
                false,
                200,
                "Category found"
        );
    }
    @Transactional(readOnly = true)
    public Response<Category> getOne(Long id){
        if (this.repository.existsCategoriesById(id)){
            return  new Response<>(
                    this.repository.findById(id).get(),
                    false,
                    200,
                    "Category found"
            );
        }else {
            return  new Response<>(
                    null,
                    true,
                    401,
                    "Category not found"
            );
        }

    }

    @Transactional(rollbackFor = SQLException.class)
    public Response<Category> insert(Category category){
        return  new Response<>(
                this.repository.saveAndFlush(category),
                false,
                200,
                "Category inserted"
        );
    }
    @Transactional(rollbackFor = SQLException.class)
    public Response<Category> update(Category category){
        return  new Response<>(
                this.repository.saveAndFlush(category),
                false,
                200,
                "Category updated"
        );
    }
    @Transactional(rollbackFor = SQLException.class)
    public Response<Void> delete(Long id){
        if (this.repository.existsCategoriesById(id)){

            this.repository.deleteById(id);
            return new Response<>(
                    null,
                    false,
                    200,
                    "Category deleted"
            );
        }else {
            return new Response<>(
                    null,
                    true,
                    401,
                    "Category not found"
            );
        }

    }

}

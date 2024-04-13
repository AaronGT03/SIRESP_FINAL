package mex.edu.utez.siresp.siresp.service.review;

import mex.edu.utez.siresp.siresp.model.review.Review;
import mex.edu.utez.siresp.siresp.model.review.ReviewRepository;
import mex.edu.utez.siresp.siresp.utils.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class ReviewService {

   private final  ReviewRepository repository;

    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Response<List<Review>> getAll(){
        return  new Response<>(
                this.repository.findAll(),
                false,
                200,
                "Review found"
        );
    }
    @Transactional(readOnly = true)
    public Response<Review> getOne(Long id){
        if (this.repository.existsReviewById(id)){
            return  new Response<>(
                    this.repository.findById(id).get(),
                    false,
                    200,
                    "Review found"
            );
        }else {
            return  new Response<>(
                    null,
                    true,
                    401,
                    "Review not found"
            );
        }

    }

    @Transactional(rollbackFor = SQLException.class)
    public Response<Review> insert(Review review){
        return  new Response<>(
                this.repository.saveAndFlush(review),
                false,
                200,
                "Review inserted"
        );
    }
    @Transactional(rollbackFor = SQLException.class)
    public Response<Review> update(Review review){
        return  new Response<>(
                this.repository.saveAndFlush(review),
                false,
                200,
                "Review updated"
        );
    }
    @Transactional(rollbackFor = SQLException.class)
    public Response<Void> delete(Long id){
        if (this.repository.existsReviewById(id)){

            this.repository.deleteById(id);
            return new Response<>(
                    null,
                    false,
                    200,
                    "Review deleted"
            );
        }else {
            return new Response<>(
                    null,
                    true,
                    401,
                    "Review not found"
            );
        }

    }
}

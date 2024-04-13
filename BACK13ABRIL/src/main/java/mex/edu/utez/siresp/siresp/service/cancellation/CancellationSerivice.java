package mex.edu.utez.siresp.siresp.service.cancellation;

import mex.edu.utez.siresp.siresp.model.cancellation.CancellarionRepository;
import mex.edu.utez.siresp.siresp.model.cancellation.Cancellation;
import mex.edu.utez.siresp.siresp.utils.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class CancellationSerivice {

    private final CancellarionRepository repository;

    public CancellationSerivice(CancellarionRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Response<List<Cancellation>> getAll(){
        return  new Response<>(
                this.repository.findAll(),
                false,
                200,
                "Cancellation found"
        );
    }
    @Transactional(readOnly = true)
    public Response<Cancellation> getOne(Long id){
        if (this.repository.existsCancellationById(id)){
            return  new Response<>(
                    this.repository.findById(id).get(),
                    false,
                    200,
                    "Cancellation found"
            );
        }else {
            return  new Response<>(
                    null,
                    true,
                    401,
                    "Cancellation not found"
            );
        }

    }

    @Transactional(rollbackFor = SQLException.class)
    public Response<Cancellation> insert(Cancellation cancellation){
        return  new Response<>(
                this.repository.saveAndFlush(cancellation),
                false,
                200,
                "Cancellation inserted"
        );
    }
    @Transactional(rollbackFor = SQLException.class)
    public Response<Cancellation> update(Cancellation cancellation){
        return  new Response<>(
                this.repository.saveAndFlush(cancellation),
                false,
                200,
                "Cancellation updated"
        );
    }

    @Transactional(rollbackFor = SQLException.class)
    public Response<Void> delete(Long id){
        if (this.repository.existsCancellationById(id)){

            this.repository.deleteById(id);
            return new Response<>(
                    null,
                    false,
                    200,
                    "Canellation Deleted"
            );
        }else {
            return new Response<>(
                    null,
                    true,
                    401,
                    "Cancellation not found"
            );
        }

    }


}

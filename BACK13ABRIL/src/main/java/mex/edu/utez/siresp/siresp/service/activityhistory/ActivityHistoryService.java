package mex.edu.utez.siresp.siresp.service.activityhistory;

import mex.edu.utez.siresp.siresp.model.activityhistory.ActivityHistory;
import mex.edu.utez.siresp.siresp.model.activityhistory.ActivityHistoryRepository;
import mex.edu.utez.siresp.siresp.utils.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityHistoryService {

    private final ActivityHistoryRepository repository;

    public ActivityHistoryService(ActivityHistoryRepository repository){
        this.repository = repository;
    }



    @Transactional(readOnly = true)
    public Response<List<ActivityHistory>> getAll(){
        return  new Response<>(
                this.repository.findAll(),
                false,
                200,
                "ActivityHistory found"
        );
    }
    @Transactional(readOnly = true)
    public Response<ActivityHistory> getOne(Long id){
        Optional<ActivityHistory> activityHistoryOptional = this.repository.findById(id);


        if (activityHistoryOptional.isPresent()){
            return  new Response<>(
                    activityHistoryOptional.get(),
                    false,
                    200,
                    "ActivityHistory found"
            );
        }else {
            return  new Response<>(
                    null,
                    true,
                    401,
                    "ActivityHistory not found"
            );
        }

    }
    @Transactional(rollbackFor = SQLException.class)
    public Response<ActivityHistory> insert(ActivityHistory activity){
        return  new Response<>(
                this.repository.saveAndFlush(activity),
                false,
                200,
                "ActivityHistory inserted"
        );
    }
    @Transactional(rollbackFor = SQLException.class)
    public Response<ActivityHistory> update(ActivityHistory activity){
        return  new Response<>(
                this.repository.saveAndFlush(activity),
                false,
                200,
                "ActivityHistory updated"
        );
    }
    @Transactional(rollbackFor = SQLException.class)
    public Response<Void> delete(Long id){
        if (this.repository.existsActivityHistoryById(id)){

            this.repository.deleteById(id);
            return new Response<>(
                    null,
                    false,
                    200,
                    "ActivityHistory deleted"
            );
        }else {
            return new Response<>(
                    null,
                    true,
                    401,
                    "ActivityHistory not found"
            );
        }

    }
}

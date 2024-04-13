package mex.edu.utez.siresp.siresp.service.pay;

import mex.edu.utez.siresp.siresp.model.pay.Pay;
import mex.edu.utez.siresp.siresp.model.pay.PayRepository;
import mex.edu.utez.siresp.siresp.utils.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class PayService {

    private final PayRepository repository;

    public PayService(PayRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Response<List<Pay>> getAll(){
        return  new Response<>(
                this.repository.findAll(),
                false,
                200,
                "Pays found"
        );
    }
    @Transactional(readOnly = true)
    public Response<Pay> getOne(Long id){
        if (this.repository.existsPayById(id)){
            return  new Response<>(
                    this.repository.findById(id).get(),
                    false,
                    200,
                    "Pay found"
            );
        }else {
            return  new Response<>(
                    null,
                    true,
                    401,
                    "Pay not found"
            );
        }

    }
    @Transactional(rollbackFor = SQLException.class)
    public Response<Pay> insert(Pay pay){
        return  new Response<>(
                this.repository.saveAndFlush(pay),
                false,
                200,
                "Pay inserted"
        );
    }
    @Transactional(rollbackFor = SQLException.class)
    public Response<Pay> update(Pay pay){
        return  new Response<>(
                this.repository.saveAndFlush(pay),
                false,
                200,
                "Pay updated"
        );
    }

    @Transactional(rollbackFor = SQLException.class)
    public Response<Void> delete(Long id){
        if (this.repository.existsPayById(id)){

            this.repository.deleteById(id);
            return new Response<>(
                    null,
                    false,
                    200,
                    "Pay deleted"
            );
        }else {
            return new Response<>(
                    null,
                    true,
                    401,
                    "Pay not found"
            );
        }

    }
}

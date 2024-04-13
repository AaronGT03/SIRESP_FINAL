package mex.edu.utez.siresp.siresp.service.host;


import mex.edu.utez.siresp.siresp.model.host.Host;
import mex.edu.utez.siresp.siresp.model.host.HostRepository;

import mex.edu.utez.siresp.siresp.utils.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class HostService {

    private  final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Response<List<Host>> getall(){
        return new Response<>(
                this.repository.findAll(),
                false,
                200,
                "Hosts founds"
        );
    }
    @Transactional(readOnly = true)
    public Response<Host> getallByIduser(Long id){
        return new Response<>(
                this.repository.findHostByUserId(id),
                false,
                200,
                "Hosts founds"
        );
    }
    @Transactional(readOnly = true)
    public Response<Host> getOne(Long id){
        if (this.repository.existsHostById(id)){
            return new Response<>(
                    this.repository.findById(id).get(),
                    false,
                    200,
                    "Host found"
            );
        }else{
            return new Response<>(
                    null,
                    true,
                    401,
                    "Host NOt found"
            );
        }



    }
    @Transactional(rollbackFor = SQLException.class)
    public Response<Host> insert(Host host){
        return new Response<>(
                this.repository.saveAndFlush(host),
                false,
                200,
                "Host inserted"
        );
    }
    @Transactional(rollbackFor = SQLException.class)
    public Response<Host> update(Host host){
        return new Response<>(
                this.repository.saveAndFlush(host),
                false,
                200,
                "Host updated"
        );
    }

    @Transactional(rollbackFor = SQLException.class)
    public Response<Void> delete(Long id){
        if (this.repository.existsHostById(id)){

            this.repository.deleteById(id);
            return new Response<>(
                    null,
                    false,
                    200,
                    "Host Deleted"
            );
        }else {
            return new Response<>(
                    null,
                    true,
                    401,
                    "Hots NOT found"
            );
        }

    }


}

package mex.edu.utez.siresp.siresp.service.reservationdetails;

import mex.edu.utez.siresp.siresp.model.reservationdetails.ReservationDetails;
import mex.edu.utez.siresp.siresp.model.reservationdetails.ReservationDetailsRepository;
import mex.edu.utez.siresp.siresp.utils.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class ReservationDetailsService {

    private final ReservationDetailsRepository repository;

    public ReservationDetailsService(ReservationDetailsRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Response<List<ReservationDetails>> getAll(){
        return  new Response<>(
                this.repository.findAll(),
                false,
                200,
                "ReservationDetails  found"
        );
    }
    @Transactional(readOnly = true)
    public Response<ReservationDetails> getOne(Long id){
        if (this.repository.existsReservationDetailsById(id)){
            return  new Response<>(
                    this.repository.findById(id).get(),
                    false,
                    200,
                    "ReservationDetails found"
            );
        }else {
            return  new Response<>(
                    null,
                    true,
                    401,
                    "ReservationDetails not found"
            );
        }

    }
    @Transactional(rollbackFor = SQLException.class)
    public Response<ReservationDetails> insert(ReservationDetails reservationDetails){
        return  new Response<>(
                this.repository.saveAndFlush(reservationDetails),
                false,
                200,
                "ReservationDetails inserted"
        );
    }
    @Transactional(rollbackFor = SQLException.class)
    public Response<ReservationDetails> update(ReservationDetails reservationDetails){
        return  new Response<>(
                this.repository.saveAndFlush(reservationDetails),
                false,
                200,
                "ReservationDetails updated"
        );
    }

    @Transactional(rollbackFor = SQLException.class)
    public Response<Void> delete(Long id){
        if (this.repository.existsReservationDetailsById(id)){
            this.repository.deleteById(id);
            return new Response<>(
                    null,
                    false,
                    200,
                    "ReservationDetails deleted"
            );
        }else {
            return new Response<>(
                    null,
                    true,
                    401,
                    "ReservationDetails not found"
            );
        }

    }

}

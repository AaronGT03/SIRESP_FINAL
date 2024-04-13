package mex.edu.utez.siresp.siresp.service.booking;

import mex.edu.utez.siresp.siresp.model.booking.Booking;
import mex.edu.utez.siresp.siresp.model.booking.BookingRepository;
import mex.edu.utez.siresp.siresp.utils.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

   private final BookingRepository repository;

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Response<List<Booking>> getAll(){
        return  new Response<>(
                this.repository.findAll(),
                false,
                200,
                "Bookings found"
        );
    }
    @Transactional(readOnly = true)
    public Response<List<Booking>> getAllByIdUser(Long id){
        return  new Response<>(
                this.repository.findAllByUserId(id),
                false,
                200,
                "Bookings found by Id user"
        );
    }
    @Transactional(readOnly = true)
    public Response<Booking> getOne(Long id){
        Optional<Booking> bookingOptional = this.repository.findById(id);

        if (bookingOptional.isPresent()){
            return  new Response<>(
                    this.repository.findById(id).get(),
                    false,
                    200,
                    "Booking found"
            );
        }else {
            return  new Response<>(
                    null,
                    true,
                    401,
                    "Booking NOT" +
                            " found"
            );
        }

    }

    @Transactional(rollbackFor = SQLException.class)
    public Response<Boolean> updateStateById(String status, Long id){
        if (this.repository.existsBookingById(id)){
            this.repository.updateStateBookingById(id,status);
            return  new Response<>(
                    null,
                    false,
                    200,
                    "Booking updated state  by Id "
            );
        }else {
            return  new Response<>(
                    null,
                    false,
                    200,
                    "Booking not found  "
            );
        }

    }

     @Transactional(rollbackFor = SQLException.class)
    public Response<Boolean> insert(String status, Long id){
        if (this.repository.existsBookingById(id)){
            this.repository.updateStateBookingById(id,status);
            return  new Response<>(
                    null,
                    false,
                    200,
                    "Booking updated state  by Id "
            );
        }else {
            return  new Response<>(
                    null,
                    false,
                    200,
                    "Booking not found  "
            );
        }
    }




    @Transactional(rollbackFor = SQLException.class)
    public Response<Booking> insert(Booking booking){
        return  new Response<>(
                this.repository.saveAndFlush(booking),
                false,
                200,
                "Booking inserted"
        );
    }
    @Transactional(rollbackFor = SQLException.class)
    public Response<Booking> update(Booking booking){
        return  new Response<>(
                this.repository.saveAndFlush(booking),
                false,
                200,
                "Booking updated"
        );
    }



    @Transactional(rollbackFor = SQLException.class)
    public Response<Void> delete(Long id){
        if(this.repository.existsBookingById(id)){

            this.repository.deleteById(id);
            return  new Response<>(
                    null,
                    false,
                    200,
                    "Booking deleted"
            );
        }else{

            return  new Response<>(
                    null,
                    true,
                    401,
                    "Booking not found"
            );
        }


    }




    }

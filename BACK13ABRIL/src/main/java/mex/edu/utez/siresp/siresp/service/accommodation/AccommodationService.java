package mex.edu.utez.siresp.siresp.service.accommodation;

import mex.edu.utez.siresp.siresp.model.accommodation.Accommodation;
import mex.edu.utez.siresp.siresp.model.accommodation.RepositoryAccommodation;
import mex.edu.utez.siresp.siresp.utils.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class AccommodationService {


    private final RepositoryAccommodation repository;

    public AccommodationService(RepositoryAccommodation repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Response<List<Accommodation>> getall(){
        return new Response<>(
                this.repository.findAll(),
                false,
                200,
                "Accommodations  found"
        );
    }
    @Transactional(readOnly = true)
    public Response<List<Accommodation>> getallByHost(Long id){
        return new Response<>(
                this.repository.findAllAccommodationByHostId(id),
                false,
                200,
                "Accommodations  found by Host"
        );
    }

    @Transactional(readOnly = true)
    public Response<Accommodation> getOne(Long id){
        if (this.repository.existsAccommodationById(id)){
            return new Response<>(
                    this.repository.findById(id).get(),
                    false,
                    200,
                    "Accommodation found By Id"
            );
        }else{
            return new Response<>(
                    null,
                    true,
                    401,
                    "Accommodation NOT found"
            );
        }
    }

    public Response<List<Accommodation>> getallByCategory(Long id){
        return new Response<>(
                this.repository.findAllAccommodationByCategoryId(id),
                false,
                200,
                "Accommodations  found By Category"
        );
    }
    public Response<List<Accommodation>> getallByNumGuest(int num){
        return new Response<>(
                this.repository.findAllAccommodationByNumGuest(num),
                false,
                200,
                "Accommodations  found By NumGuest"
        );
    }

    public Response<List<Accommodation>> getallByPrice(double price){
        return new Response<>(
                this.repository.findAllAccommodationByPriceLessThanEqual(price),
                false,
                200,
                "Accommodations  found By Price "
        );
    }
    public Response<List<Accommodation>> getallByLocation(String location){
        return new Response<>(
                this.repository.findAllAccommodationByLocationContaining(location),
                false,
                200,
                "Accommodations  found By Location"
        );
    }

    @Transactional(rollbackFor = SQLException.class)
    public Response<Accommodation> insert(Accommodation accommodation){
        return new Response<>(
                this.repository.saveAndFlush(accommodation),
                false,
                200,
                "Accommodation inserted"
        );
    }

    @Transactional(rollbackFor = SQLException.class)
    public Response<Accommodation> update(Accommodation accommodation){
        return new Response<>(
                this.repository.saveAndFlush(accommodation),
                false,
                200,
                "Accommodation Updated"
        );
    }
    @Transactional(rollbackFor = SQLException.class)
    public Response<Boolean> updateStatusById(String status, Long id){
        if(this.repository.existsAccommodationById(id)){
            this.repository.updateStatusById(id,status);
            return new Response<>(
                    null,
                    false,
                    200,
                    "Status Accommodation Updated"
        );
        }else{
            return new Response<>(
                   null,
                false,
                    200,
                    "Accommodation not found"
        );
        }

    }

    @Transactional(rollbackFor = SQLException.class)
    public Response<Void> delete(Long id){
        if (this.repository.existsAccommodationById(id)){

            this.repository.deleteAccommodationById(id);
            return new Response<>(
                    null,
                    false,
                    200,
                    "Accommodation Deleted"
            );
        }else {
            return new Response<>(
                    null,
                    true,
                    401,
                    "Accommodation NOt found"
            );
        }

    }




}

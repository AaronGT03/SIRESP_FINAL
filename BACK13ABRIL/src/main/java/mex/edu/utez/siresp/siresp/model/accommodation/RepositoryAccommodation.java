package mex.edu.utez.siresp.siresp.model.accommodation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositoryAccommodation extends JpaRepository<Accommodation,Long> {

    public void deleteAccommodationById(Long id);

    public List<Accommodation> findAllAccommodationByCategoryId(Long id);
    public List<Accommodation> findAllAccommodationByNumGuest(int num);
    public List<Accommodation> findAllAccommodationByPriceLessThanEqual(double price);
    public List<Accommodation> findAllAccommodationByLocationContaining(String location);
    public boolean existsAccommodationById(Long id);

    public List<Accommodation> findAllAccommodationByHostId(Long id);
    @Modifying
    @Query("UPDATE Accommodation a SET a.availability = :status WHERE a.id = :id")
    public void updateStatusById(Long id, String status);
}

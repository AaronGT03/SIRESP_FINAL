package mex.edu.utez.siresp.siresp.model.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    public boolean existsBookingById(Long id);

    @Modifying
    @Query ("UPDATE Booking b SET b.stateBooking = :role WHERE b.id = :id")
    public void updateStateBookingById(Long id,String role);

    public List<Booking> findAllByUserId(Long id);

}

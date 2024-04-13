package mex.edu.utez.siresp.siresp.model.reservationdetails;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationDetailsRepository extends JpaRepository<ReservationDetails,Long> {
    public  boolean existsReservationDetailsById(Long id);

}

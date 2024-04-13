package mex.edu.utez.siresp.siresp.controller.reservationdetails.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mex.edu.utez.siresp.siresp.model.reservationdetails.ReservationDetails;
import mex.edu.utez.siresp.siresp.model.booking.Booking;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationDetailsDto {
    private Long id;
    private Date dateArrivalGuest;
    private Date dateDepartureGuest;
    private Booking booking;

    public ReservationDetails castTocast(){
        return new ReservationDetails(
                getId(),
                getDateArrivalGuest(),
                getDateDepartureGuest(),
                getBooking()
        );
    }

}

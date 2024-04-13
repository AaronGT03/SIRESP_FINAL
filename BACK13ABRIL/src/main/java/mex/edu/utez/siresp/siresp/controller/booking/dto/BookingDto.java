package mex.edu.utez.siresp.siresp.controller.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mex.edu.utez.siresp.siresp.model.accommodation.Accommodation;
import mex.edu.utez.siresp.siresp.model.booking.Booking;
import mex.edu.utez.siresp.siresp.model.pay.Pay;
import mex.edu.utez.siresp.siresp.model.user.User;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingDto {
    private Long id ;
    private Date arrivalDate;
    private Date departureDate;
    private int numGuests;
    private String stateBooking;
    private User user;
    private  Accommodation accommodation;
    private List<Pay> pays;


    public Booking castToCast(){
        return new Booking(
                getId(),
                getArrivalDate(),
                getDepartureDate(),
                getNumGuests(),
                getStateBooking(),
                getUser(),
                getAccommodation(),
                null



        );
    }
}

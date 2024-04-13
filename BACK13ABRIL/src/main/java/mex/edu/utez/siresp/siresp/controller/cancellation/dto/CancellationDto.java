package mex.edu.utez.siresp.siresp.controller.cancellation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mex.edu.utez.siresp.siresp.model.booking.Booking;
import mex.edu.utez.siresp.siresp.model.cancellation.Cancellation;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CancellationDto {
    private  Long id;
    private String reason;
    private Booking booking;

    public Cancellation castToCast(){
        return new Cancellation(
          getId(),
          getReason(),
          getBooking()
        );
    }
}

package mex.edu.utez.siresp.siresp.controller.pay.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mex.edu.utez.siresp.siresp.model.booking.Booking;
import mex.edu.utez.siresp.siresp.model.pay.Pay;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PayDto {
    private Long id;
    private Date paymentDate;
    private int amount ;
    private String paymentStatus;
    private Booking booking;
    public Pay castToCast(){
        return  new Pay(
                getId(),
                getPaymentDate(),
                getAmount(),
                getPaymentStatus(),
                getBooking()
        );
    }

}

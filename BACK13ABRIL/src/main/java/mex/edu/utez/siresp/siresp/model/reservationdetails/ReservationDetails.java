package mex.edu.utez.siresp.siresp.model.reservationdetails;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mex.edu.utez.siresp.siresp.model.booking.Booking;

import java.util.Date;

@Entity
@Table(name = "reservationDetails")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReservationDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date dateArrivalGuest;

    @Temporal(TemporalType.DATE)
    private Date dateDepartureGuest;



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_booking", referencedColumnName = "id", nullable = false)
    private Booking booking;

}

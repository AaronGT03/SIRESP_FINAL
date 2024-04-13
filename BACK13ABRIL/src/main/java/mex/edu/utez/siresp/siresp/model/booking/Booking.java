package mex.edu.utez.siresp.siresp.model.booking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mex.edu.utez.siresp.siresp.model.accommodation.Accommodation;
import mex.edu.utez.siresp.siresp.model.pay.Pay;
import mex.edu.utez.siresp.siresp.model.user.User;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "booking")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Temporal(TemporalType.DATE)
    private Date arrivalDate;

    @Temporal(TemporalType.DATE)
    private Date departureDate;

    private int numGuests;

    private String stateBooking;


    @ManyToOne
    @JoinColumn(name = "id_user",nullable = false)
    private User user;


    @ManyToOne
    @JoinColumn(name = "id_accommodation",nullable = false)
    private Accommodation accommodation;

    @OneToMany(mappedBy = "booking")
    @JsonIgnore
    private List<Pay> pays;

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", arrivalDate=" + arrivalDate +
                ", departureDate=" + departureDate +
                ", numGuests=" + numGuests +
                ", stateBooking='" + stateBooking + '\'' +
                ", user=" + user +
                ", accommodation=" + accommodation +
                ", pays=" + pays +
                '}';
    }

}

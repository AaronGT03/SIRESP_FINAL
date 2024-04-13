package mex.edu.utez.siresp.siresp.model.pay;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mex.edu.utez.siresp.siresp.model.booking.Booking;

import java.util.Date;

@Entity
@Table(name = "pay")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
    private int amount ;
    private String paymentStatus;

    @ManyToOne
    @JoinColumn(name = "id_booking",nullable = false)
    private Booking booking;

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", amount=" + amount +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", booking=" + booking +
                '}';
    }

}

package mex.edu.utez.siresp.siresp.model.cancellation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mex.edu.utez.siresp.siresp.model.booking.Booking;

@Entity
@Table(name = "cancellation")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Cancellation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String reason;

    @OneToOne()
    @JoinColumn(name = "id_booking",  nullable = false)
    private Booking booking;




    @Override
    public String toString() {
        return "Pay{" +
                "id=" + id +
                ", reason='" + reason + '\'' +
                ", booking=" + booking +
                '}';
    }

}

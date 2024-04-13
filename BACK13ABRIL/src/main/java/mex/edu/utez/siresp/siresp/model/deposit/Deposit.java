package mex.edu.utez.siresp.siresp.model.deposit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mex.edu.utez.siresp.siresp.model.booking.Booking;

@Entity
@Table(name = "deposit")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    private String state;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_booking", referencedColumnName = "id", nullable = false)
    private Booking booking;
}

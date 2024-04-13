package mex.edu.utez.siresp.siresp.model.accommodation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import mex.edu.utez.siresp.siresp.model.category.Category;
import mex.edu.utez.siresp.siresp.model.booking.Booking;
import mex.edu.utez.siresp.siresp.model.host.Host;
import mex.edu.utez.siresp.siresp.model.review.Review;

import java.util.List;

@Entity
@Table(name = "accommodation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
    private String name;
    private String description;
    private String location;
    private double price;
    private int numGuest;
    private int numBeds;
    private int numBathrooms;
    private int numRooms;
    private String images;
    private String additionalfeatures;
    private String availability;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "accommodation")
    @JsonIgnore
    private List<Booking> bookings;
    @ManyToOne
    @JoinColumn(name = "id_category",nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "id_host",nullable = false)
    private Host host;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "accommodation")
    @JsonIgnore
    private List<Review> reviews;

    @Override
    public String toString() {
        return "Accommodation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", price=" + price +
                ", numRooms=" + numRooms +
                ", numGuest=" + numGuest +
                ", numBeds=" + numGuest +
                ", numBathrooms=" + numBathrooms +
                ", images='" + images + '\'' +
                ", additionalfeatures='" + additionalfeatures + '\'' +
                ", availability='" + availability + '\'' +
                '}';
    }

}

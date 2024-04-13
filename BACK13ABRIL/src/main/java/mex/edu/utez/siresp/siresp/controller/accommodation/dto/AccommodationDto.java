package mex.edu.utez.siresp.siresp.controller.accommodation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mex.edu.utez.siresp.siresp.model.category.Category;
import mex.edu.utez.siresp.siresp.model.accommodation.Accommodation;
import mex.edu.utez.siresp.siresp.model.booking.Booking;
import mex.edu.utez.siresp.siresp.model.host.Host;
import mex.edu.utez.siresp.siresp.model.review.Review;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccommodationDto {
    private Long id;
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
    private List<Booking> bookings;
    private Category category;
    private Host host;
    private List<Review> reviews;

    public Accommodation castToCast(){
        return  new Accommodation(
               getId(),
                getName(),
                getDescription(),
                getLocation(),
                getPrice(),
                getNumGuest(),
                getNumBeds(),
                getNumBathrooms(),
                getNumRooms(),
                getImages(),
                getAdditionalfeatures(),
                getAvailability(),
                getBookings(),
                getCategory(),
                getHost(),
                getReviews()
        );
    }

}

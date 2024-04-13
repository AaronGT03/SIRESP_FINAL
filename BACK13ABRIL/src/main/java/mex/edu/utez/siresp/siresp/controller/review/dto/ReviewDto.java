package mex.edu.utez.siresp.siresp.controller.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mex.edu.utez.siresp.siresp.model.accommodation.Accommodation;
import mex.edu.utez.siresp.siresp.model.review.Review;
import mex.edu.utez.siresp.siresp.model.user.User;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewDto {
    private Long id;
    private String description;
    private int average;
    private User user;
    private Accommodation accommodation;
    public Review castToCast(){
        return  new Review(
                getId(),
                getDescription(),
                getAverage(),
                getUser(),
                getAccommodation()
        );
    }

}

package mex.edu.utez.siresp.siresp.controller.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mex.edu.utez.siresp.siresp.model.activityhistory.ActivityHistory;
import mex.edu.utez.siresp.siresp.model.booking.Booking;
import mex.edu.utez.siresp.siresp.model.review.Review;
import mex.edu.utez.siresp.siresp.model.user.User;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    private String names;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private Date date;
    private String gender;
    private String profilePicture;
    private String role;
    private String username;
    private  String password;
    private  String status;
    private List<ActivityHistory>  history;
    private List<Booking> bookings;
    private  List<Review> reviews;

    public User castTocast(){
        return new User(
                getId(),
                getNames(),
                getLastName(),
                getEmail(),
                getPhone(),
                getAddress(),
                getDate(),
                getGender(),
                getProfilePicture(),
                getRole(),
                getUsername(),
                getPassword(),
                getStatus(),
                null,
                null,
                null
        );
    }
}

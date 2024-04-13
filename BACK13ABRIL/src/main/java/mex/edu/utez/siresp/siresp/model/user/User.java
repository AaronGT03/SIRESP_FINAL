package mex.edu.utez.siresp.siresp.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import mex.edu.utez.siresp.siresp.model.activityhistory.ActivityHistory;
import mex.edu.utez.siresp.siresp.model.booking.Booking;
import mex.edu.utez.siresp.siresp.model.review.Review;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private String names;
    @Column(length = 20)
    private String lastName;
    @Column(length = 30)
    private String email;
    @Column(length = 10)
    private String phone;
    @Column(length = 500)
    private String address;
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(length = 9)
    private String gender;
    private String profilePicture;
    private String role;
    private String username;
    private String password;
    private String status;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ActivityHistory> history;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Booking> bookings;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Review> reviews;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", names='" + names + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", date=" + date +
                ", gender='" + gender + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", role='" + role + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                '}';
    }


}

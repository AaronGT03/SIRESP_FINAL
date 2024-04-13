package mex.edu.utez.siresp.siresp.model.review;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mex.edu.utez.siresp.siresp.model.accommodation.Accommodation;
import mex.edu.utez.siresp.siresp.model.user.User;

@Entity
@Table(name = "review")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private int average;

    @ManyToOne
    @JoinColumn(name = "id_user",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_accomodation",nullable = false)
    private Accommodation accommodation;
    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", average=" + average +
                ", user=" + user +
                ", accommodation=" + accommodation +
                '}';
    }


}

package mex.edu.utez.siresp.siresp.model.activityhistory;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mex.edu.utez.siresp.siresp.model.user.User;

@Entity
@Table(name = "activityHistory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActivityHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String action;
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_user",nullable = false)
    private User user;

    @Override
    public String toString() {
        return "Pay{" +
                "id=" + id +
                ", action='" + action + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }


}

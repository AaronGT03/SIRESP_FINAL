package mex.edu.utez.siresp.siresp.model.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mex.edu.utez.siresp.siresp.model.accommodation.Accommodation;

import java.util.List;

@Entity
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name ;
    private String description ;
    private String icon;
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Accommodation> accommodations;

    public String toString() {
        return "ID: " + id +
                ", Name: " + name +
                ", Description: " + description +
                ", Icon: " + icon +
                ", Accommodations: " + accommodations;
    }

}

package mex.edu.utez.siresp.siresp.model.host;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import mex.edu.utez.siresp.siresp.model.accommodation.Accommodation;
import mex.edu.utez.siresp.siresp.model.user.User;

import java.util.List;

@Entity
@Table(name = "host")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Host {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String curp;
    private String identificationImage;
    private String rfc;

    @OneToOne()
    @JoinColumn(name = "usuario_Id", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "host")
    @JsonIgnore
    private List<Accommodation> accommodations;

    @Override
    public String toString() {
        return "Host{" +
                "id=" + id +
                ", curp='" + curp + '\'' +
                ", identificationImage='" + identificationImage + '\'' +
                ", Rfc='" + rfc + '\'' +
                ", usuario=" + user.toString() + // Llama al toString() de User si también lo has implementado
                '}';
    }

}

package mex.edu.utez.siresp.siresp.controller.host.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mex.edu.utez.siresp.siresp.model.accommodation.Accommodation;
import mex.edu.utez.siresp.siresp.model.host.Host;
import mex.edu.utez.siresp.siresp.model.user.User;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HostDto {
    private Long id;
    private String curp;
    private String identificationImage;
    private String rfc;
    private User user;
    private List<Accommodation> accommodations;
    public Host casToCast(){
        return  new Host(
                getId(),
                getCurp(),
                getIdentificationImage(),
                getRfc(),
                getUser(),
                null
        );
    }

}

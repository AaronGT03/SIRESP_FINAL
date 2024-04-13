package mex.edu.utez.siresp.siresp.model.host;

import mex.edu.utez.siresp.siresp.model.accommodation.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HostRepository extends JpaRepository<Host, Long> {

    public boolean existsHostById(Long id);
    public void deleteHostById(Long id);
    public Host findHostByUserId(Long id);

}

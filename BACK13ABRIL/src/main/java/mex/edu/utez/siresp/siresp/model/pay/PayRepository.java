package mex.edu.utez.siresp.siresp.model.pay;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository  extends JpaRepository<Pay,Long> {

    public boolean existsPayById(Long id);
}

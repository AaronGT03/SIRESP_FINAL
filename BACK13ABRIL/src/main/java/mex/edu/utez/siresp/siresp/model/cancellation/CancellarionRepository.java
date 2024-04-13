package mex.edu.utez.siresp.siresp.model.cancellation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CancellarionRepository extends JpaRepository<Cancellation,Long> {

    public boolean existsCancellationById(Long id);

}

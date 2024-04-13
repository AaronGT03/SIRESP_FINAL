package mex.edu.utez.siresp.siresp.model.activityhistory;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityHistoryRepository extends JpaRepository<ActivityHistory,Long> {
    public  boolean existsActivityHistoryById(Long id);


}

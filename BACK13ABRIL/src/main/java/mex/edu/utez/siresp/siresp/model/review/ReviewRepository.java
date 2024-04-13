package mex.edu.utez.siresp.siresp.model.review;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    public boolean existsReviewById(Long id);
}

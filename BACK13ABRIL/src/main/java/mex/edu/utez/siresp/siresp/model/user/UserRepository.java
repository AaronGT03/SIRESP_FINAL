package mex.edu.utez.siresp.siresp.model.user;

import mex.edu.utez.siresp.siresp.model.accommodation.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    public void deleteUserById(Long id);
    Optional<User> findUserByUsername(String username);

    @Modifying
    @Query("UPDATE User u SET u.status = :status WHERE u.id = :id")
    public void updateStatusById(Long id, String status);

    @Modifying
    @Query("UPDATE User u SET u.role = :role WHERE u.id = :id")
    public void updateRoleById(Long id, String role);
    public boolean existsUserById(Long id);



}

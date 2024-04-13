package mex.edu.utez.siresp.siresp.service.user;

import mex.edu.utez.siresp.siresp.model.user.User;
import mex.edu.utez.siresp.siresp.model.user.UserRepository;
import mex.edu.utez.siresp.siresp.utils.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

   private final  UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Response<List<User>> getall(){
        return new Response<>(
                this.repository.findAll(),
                false,
                200,
                "Users found"
        );
    }
    @Transactional(readOnly = true)
    public Response<Optional<User>> getByUsername(String username){
        if (this.repository.findUserByUsername(username)!=null){
            return new Response<>(
                    this.repository.findUserByUsername(username),
                    false,
                    200,
                    "User found by Username"
            );
        }else {
            return new Response<>(
                    null,
                    false,
                    201,
                    "User NOT found by Username"
            );
        }
    }

    @Transactional(readOnly = true)
    public Response<User> getOne(Long id){
        if (this.repository.existsUserById(id)){
            return new Response<>(
                    this.repository.findById(id).get(),
                    false,
                    200,
                    "User found"
            );
        }else{
            return new Response<>(
                    null,
                    false,
                    200,
                    "User NOT found"
            );
        }



    }
    @Transactional(rollbackFor = SQLException.class)
    public Response<User> insert(User user){
        return new Response<>(
                this.repository.saveAndFlush(user),
                false,
                200,
                "Usuario Inserted"
        );
    }
    @Transactional(rollbackFor = SQLException.class)
    public Response<Boolean> updateStatus(Long id, String status){
        if(this.repository.existsUserById(id)){
            this.repository.updateStatusById(id,status);
            return new Response<>(
                    null,
                    false,
                    200,
                    "Status updated"
            );
        }else {
            return new Response<>(
                    null,
                    false,
                    200,
                    "user not found"
            );
        }

    }
    @Transactional(rollbackFor = SQLException.class)
    public Response<Boolean> updateRole(Long id, String role){
        if(this.repository.existsUserById(id)){
            this.repository.updateRoleById(id,role);
            return new Response<>(
                    null,
                    false,
                    200,
                    "ROL updated"
            );
        }else {
            return new Response<>(
                    null,
                    false,
                    200,
                    "user not found"
            );
        }

    }
    @Transactional(rollbackFor = SQLException.class)
    public Response<User> update(User user){
        return new Response<>(
                this.repository.saveAndFlush(user),
                false,
                200,
                "User Updated"
        );
    }

    @Transactional(rollbackFor = SQLException.class)
    public Response<Void> delete(Long id){
        if (this.repository.existsUserById(id)){

            this.repository.deleteById(id);
            return new Response<>(
                    null,
                    false,
                    200,
                    "User Deleted"
            );
        }else {
            return new Response<>(
                    null,
                    true,
                    401,
                    "User NOT found"
            );
        }

    }



}

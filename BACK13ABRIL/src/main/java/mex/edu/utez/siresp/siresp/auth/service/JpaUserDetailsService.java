package mex.edu.utez.siresp.siresp.auth.service;

import mex.edu.utez.siresp.siresp.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository repository;
     public JpaUserDetailsService(UserRepository repository){
         this.repository=repository;
     }



    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<mex.edu.utez.siresp.siresp.model.user.User> o = repository.findUserByUsername(username);

        if(!o.isPresent()){
           throw  new UsernameNotFoundException(String.format("Username %s no existe en el sistema",username));
        }
        mex.edu.utez.siresp.siresp.model.user.User user= o.orElseThrow();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));



        return new User(user.getUsername(),user.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }
}
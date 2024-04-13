package mex.edu.utez.siresp.siresp.auth;


import mex.edu.utez.siresp.siresp.auth.filters.JwtAuthenticationFilter;
import mex.edu.utez.siresp.siresp.auth.filters.JwtValidationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class SpringSecurityConfig {

    private final  AuthenticationConfiguration authenticationConfiguration;

    public SpringSecurityConfig(AuthenticationConfiguration authenticationConfiguration){
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager authenticationManager() throws  Exception{
        return  authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/api-sirep/email/").permitAll()
                .requestMatchers(HttpMethod.GET, "/api-sirep/user/").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/api-sirep/user/{id}").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/api-sirep/user/username/{username}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api-sirep/user/").permitAll()
                .requestMatchers(HttpMethod.POST, "/api-sirep/user/actualizar/").hasAnyRole("USER","ADMIN","HOST")
                .requestMatchers(HttpMethod.POST, "/api-sirep/user/actualizar/status").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.POST, "/api-sirep/user/actualizar/rol").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api-sirep/user/{id}/").hasAnyRole("USER","ADMIN","HOST")
                .requestMatchers(HttpMethod.POST, "/api-sirep/user/upload").permitAll()
                .requestMatchers(HttpMethod.POST, "/api-sirep/host/").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/api-sirep/host/").permitAll()
                .requestMatchers(HttpMethod.GET, "/api-sirep/host/{id}").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/api-sirep/host/user/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api-sirep/host/upload").permitAll()
                .requestMatchers(HttpMethod.POST, "/api-sirep/host/actualizar").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api-sirep/host/{id}").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/api-sirep/accommodation/").permitAll()
                .requestMatchers(HttpMethod.GET, "/api-sirep/accommodation/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api-sirep/accommodation/host/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api-sirep/accommodation/category/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api-sirep/accommodation/numGuest/{num}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api-sirep/accommodation/price/{price}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api-sirep/accommodation/location/{location}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api-sirep/accommodation/").hasAnyRole("USER","ADMIN","HOST")
                .requestMatchers(HttpMethod.POST, "/api-sirep/accommodation/upload").permitAll()
                .requestMatchers(HttpMethod.POST, "/api-sirep/accommodation/actualizar").hasAnyRole("HOST")
                .requestMatchers(HttpMethod.POST, "/api-sirep/accommodation/status/").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api-sirep/accommodation/{id}").hasAnyRole("USER","ADMIN","HOST")
                .requestMatchers(HttpMethod.GET, "/api-sirep/activity/").hasAnyRole("USER")
                .requestMatchers(HttpMethod.GET, "/api-sirep/activity/{id}").hasAnyRole("USER")
                .requestMatchers(HttpMethod.POST, "/api-sirep/activity/").hasAnyRole("USER")
                .requestMatchers(HttpMethod.POST, "/api-sirep/activity/actualizar").hasAnyRole("USER")
                .requestMatchers(HttpMethod.DELETE, "/api-sirep/activity/{id}").hasAnyRole("USER")
                .requestMatchers(HttpMethod.GET, "/api-sirep/booking/").hasAnyRole("USER","ADMIN","HOST")
                .requestMatchers(HttpMethod.GET, "/api-sirep/booking/{id}").hasAnyRole("USER","ADMIN","HOST")
                .requestMatchers(HttpMethod.POST, "/api-sirep/booking/").hasAnyRole("USER","HOST")
                .requestMatchers(HttpMethod.POST, "/api-sirep/booking/actualizar").hasAnyRole("USER")
                .requestMatchers(HttpMethod.POST, "/api-sirep/booking/actualizar/state/").hasAnyRole("USER","HOST","ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api-sirep/booking/{id}").hasAnyRole("USER")
                .requestMatchers(HttpMethod.GET, "/api-sirep/cancellation/").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/api-sirep/cancellation/{id}").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.POST, "/api-sirep/cancellation/").hasAnyRole("USER","ADMIN","HOST")
                .requestMatchers(HttpMethod.POST, "/api-sirep/cancellation/actualizar").hasAnyRole("USER","ADMIN","HOST")
                .requestMatchers(HttpMethod.DELETE, "/api-sirep/cancellation/{id}").hasAnyRole("USER","ADMIN","HOST")
                .requestMatchers(HttpMethod.GET, "/api-sirep/pay/").hasAnyRole( "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api-sirep/pay/{id}").hasAnyRole("USER","HOST")
                .requestMatchers(HttpMethod.POST, "/api-sirep/pay/").hasAnyRole("USER","HOST")
                .requestMatchers(HttpMethod.POST, "/api-sirep/pay/actualizar").hasAnyRole("USER","HOST")
                .requestMatchers(HttpMethod.DELETE, "/api-sirep/pay/{id}").hasAnyRole("USER")
                .requestMatchers(HttpMethod.GET, "/api-sirep/review/").hasAnyRole("USER","HOST","ADMIN")
                .requestMatchers(HttpMethod.GET, "/api-sirep/review/{id}").hasAnyRole("USER","HOST","ADMIN")
                .requestMatchers(HttpMethod.POST, "/api-sirep/review/").hasAnyRole("USER","HOST","ADMIN")
                .requestMatchers(HttpMethod.POST, "/api-sirep/review/actualizar").hasAnyRole("USER","HOST","ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api-sirep/review/{id}").hasAnyRole("USER","HOST","ADMIN")
                .requestMatchers(HttpMethod.GET, "/api-sirep/category/").permitAll()
                .requestMatchers(HttpMethod.GET, "/api-sirep/category/{id}").hasAnyRole("USER")
                .requestMatchers(HttpMethod.POST, "/api-sirep/category/").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.POST, "/api-sirep/category/actualizar").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api-sirep/category/{id}").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/api-sirep/detail/").hasAnyRole("USER")
                .requestMatchers(HttpMethod.GET, "/api-sirep/detail/{id}").hasAnyRole("USER")
                .requestMatchers(HttpMethod.POST, "/api-sirep/detail/").hasAnyRole("USER")
                .requestMatchers(HttpMethod.POST, "/api-sirep/detail/actualizar").hasAnyRole("USER")
                .requestMatchers(HttpMethod.DELETE, "/api-sirep/detail/{id}").hasAnyRole("USER")



                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager()))
                .csrf(config -> config.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors->cors.configurationSource(corsConfigurationSource()))
                .build();

    }
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);
        return  source;

    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;

    }
}

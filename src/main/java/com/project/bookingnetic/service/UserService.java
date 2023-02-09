package com.project.bookingnetic.service;

import ch.qos.logback.core.model.Model;
import com.project.bookingnetic.models.Accommodation;
import com.project.bookingnetic.models.Reservation;
import com.project.bookingnetic.models.RoleType;
import com.project.bookingnetic.models.User;
import com.project.bookingnetic.repository.AccommodationRepository;
import com.project.bookingnetic.repository.ReservationRepository;
import com.project.bookingnetic.repository.UserRepository;
import com.project.bookingnetic.security.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repo;
    private final AccommodationRepository accommodationRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    public UserService(UserRepository repo,
                       AccommodationRepository accommodationRepository) {
        this.repo = repo;
        this.accommodationRepository = accommodationRepository;
    }

    public List<User> get(){
        return new ArrayList<>(repo.findAll());
    }

    public User save(User user){
        return repo.save(user);
    }

    public ResponseEntity<HttpStatus> deleteById(Long id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public User findById(Long id){
       return repo.findById(id).get();
    }


    public boolean userExistsByEmail(String email) {
        Optional<User> user = Optional.ofNullable(repo.findByEmail(email));
        return user.isPresent() ? true : false;
    }


    public Optional<User> findByEmail(String email){
        Optional<User> user = Optional.ofNullable(repo.findByEmail(email));
        return user;
    }

    public ModelAndView showAccount(long id){

        ModelAndView mav = new ModelAndView();
        Optional<User> opt = repo.findById(id);

        opt.ifPresent(user -> {
            mav.addObject("user", user);
            List<Accommodation> accommodations = accommodationRepository.getAccommodationsByUserFk(user.getId());
            List<Reservation> reservations = reservationRepository.getReservationsByUserFk(user.getId());
            mav.addObject("accommodations",accommodations);
            mav.addObject("reservations",reservations);
        });
        mav.setViewName("/account");

        return mav;
    }

    public boolean register(User user){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnumRole(RoleType.ROLE_USER);
        user.setRegistrationDate(LocalDate.now());

        try{
            repo.save(user);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public User update(User user, Long id) {
        if(repo.findById(id).isPresent()){
            User update = repo.findById(id).get();
            update.updateUser(user.getFirstName(), user.getLastName(), user.getEmail(),
                                 user.getPhone());
            return repo.save(update);
        }
        return null;
    }

    public User hashAndSaveUser(User user){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.repo.findByEmail(email);
        UserSecurity userSecurity = new UserSecurity(user);
        return userSecurity;
    }

    public String addAccommodationToUser(Accommodation acc, long user_id) {
        AtomicReference<String> view = new AtomicReference<>("notSuccess");

        Optional<User> userOptional = repo.findById(user_id);
        userOptional.ifPresent((user)-> {
            acc.setUser(user);
            acc.setCreation_date(LocalDate.now());
            accommodationRepository.save(acc);
            view.set("redirect:/accommodation/"+acc.getId());
        });

        return view.toString();
    }
}

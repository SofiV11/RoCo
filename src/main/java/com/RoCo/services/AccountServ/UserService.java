package com.RoCo.services.AccountServ;

import com.RoCo.entities.Account.UserRole;
import com.RoCo.models.SiteUserDto;
import com.RoCo.repositories.AccountRepo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.RoCo.entities.Account.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Component
@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;


    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepo.findFirstByName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User with name %s not found".formatted(userName));
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User( // for security
                user.getName(),
                user.getPassword(),
                roles
        );
    }


    public boolean save(SiteUserDto userDto) {
        if (!Objects.equals(userDto.getPassword(), userDto.getMatchingPassword())) {
            throw new RuntimeException("password is not equals");
        }
//         User user = User.builder()
//                .name(userDto.getUsername())
//                .password(encoder.encode(userDto.getPassword()))
//                .email(userDto.getEmail())
//                .role(UserRole.UNKNOWN)
//                .build();

        User user = new User (userDto.getUsername(), encoder.encode(userDto.getPassword()), userDto.getEmail(), UserRole.CLIENT);

        userRepo.save(user);
        return true;
    }
    public String findLoggedInUsername() {
        Object userDetails =
                SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails) userDetails).getUsername();
        }
        return null;
    }

}

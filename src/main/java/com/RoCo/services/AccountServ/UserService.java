package com.RoCo.services.AccountServ;

import com.RoCo.entities.Account.ProfileImageEnt;
import com.RoCo.entities.Account.UserDetailsEnt;
import com.RoCo.entities.Account.UserRole;
import com.RoCo.models.SiteUserDto;
import com.RoCo.repositories.AccountRepo.ProfileImageRepo;
import com.RoCo.repositories.AccountRepo.UserDetailsRepo;
import com.RoCo.repositories.AccountRepo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.RoCo.entities.Account.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Component
@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final MailSender mailSender;

    private final UserDetailsRepo userDetailsRepo;

    private final ProfileImageRepo imageRepo;

    private StringBuilder errorBuffer;


    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder encoder, MailSender mailSender, UserDetailsRepo userDetailsRepo, ProfileImageRepo imageRepo) {
        this.imageRepo = imageRepo;
        this.errorBuffer = new StringBuilder();
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.mailSender = mailSender;
        this.userDetailsRepo = userDetailsRepo;
    }

    public StringBuilder getErrorBuffer() {
        return errorBuffer;
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


    //@Transactional
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
        if (!StringUtils.isEmpty(user.getEmail()) && !user.getEmail().equals("")){
            String code = UUID.randomUUID().toString();
            UserDetailsEnt userDetailsEnt = new UserDetailsEnt(user, code);
            userDetailsRepo.save(userDetailsEnt);
            String message = "Activate account with code: " + "\n" + code;
            mailSender.sendMail(user.getEmail(), "!!!!", message);
        }
        return true;
    }

    public User saveAndGet(SiteUserDto userDto, @Nullable MultipartFile profileImage){
        if (!Objects.equals(userDto.getPassword(), userDto.getMatchingPassword())) {
            //throw new RuntimeException("password is not equals");
            errorBuffer.append("password is not equals");
            return null;
        }
        if(userRepo.findFirstByName(userDto.getUsername())!=null){
            errorBuffer.append("User having this username already exists");
            return null;
        }
//         User user = User.builder()
//                .name(userDto.getUsername())
//                .password(encoder.encode(userDto.getPassword()))
//                .email(userDto.getEmail())
//                .role(UserRole.UNKNOWN)
//                .build();

        //check is user already exists
        //if (profileImage.getSize() == 0) {
            User user = new User(
                    userDto.getUsername(),
                    encoder.encode(userDto.getPassword()),
                    userDto.getEmail(),
                    UserRole.CLIENT,
                    false);
        UserDetailsEnt userDetailsEnt = new UserDetailsEnt();
       // } else {


        ProfileImageEnt profileImageEnt = toImageEntity(profileImage);
        userDetailsEnt.addImageToUser(profileImageEnt);

        //}


        try {
            userRepo.save(user);
        } catch (Exception e) {
            errorBuffer.append(e.toString());
            return null;
        }


        if (!StringUtils.isEmpty(user.getEmail()) && !user.getEmail().equals("")){
            String code = UUID.randomUUID().toString();
            userDetailsEnt.setUser(user);
            user.setUserDetails(userDetailsEnt);
            userDetailsEnt.setActivationCode(code);//= new UserDetailsEnt(user, code);
            String message = "Activate account with code: " + "\n" + code;
            mailSender.sendMail(user.getEmail(), "!!!!", message);
        }
        userDetailsRepo.save(userDetailsEnt);
        errorBuffer.setLength(0);
        return user;
    }
    public boolean editUser(User user){
        User userWithEqualsName = userRepo.findFirstByName(user.getName());
        if(userWithEqualsName!=null && (Objects.equals(userWithEqualsName.getUserId(), user.getUserId()))){
            errorBuffer.append("User having this username already exists");
            return false;
        }

        try {
            userRepo.save(user);
        } catch (Exception e) {
            errorBuffer.append(e.toString());
            return false;
        }
        errorBuffer.setLength(0);
        return true;
    }

//    public void createUserDetails(User user){
//        UserDetailsEnt userDetailsEnt = new UserDetailsEnt(user);
//        userDetailsRepo.save(userDetailsEnt);
//        user.setUserDetails(userDetailsEnt);
//        userRepo.save(user);
//    }

    public boolean editUserDetails(UserDetailsEnt userDetailsEnt, @Nullable MultipartFile profileImage){

//        if(!userRepo.existsById(user.getUser().getUserId())) return null;

/*        User currentUser = userDetailsEnt.getUser();
        currentUser.setUserDetails(userDetailsEnt);*/

        if(profileImage != null) {
            if(profileImage.getSize()==0){
                byte[] bytes = new byte[0];
                userDetailsEnt.setImage(new ProfileImageEnt());
                userDetailsEnt.getImage().setBytes(bytes);
                Integer zero = 0;
                userDetailsEnt.getImage().setSize(zero.longValue());
            } else {
                ProfileImageEnt profileImageEnt = toImageEntity(profileImage);
                if(userDetailsEnt.getImage().getImageId()!=null){
                    profileImageEnt.setImageId(userDetailsEnt.getImage().getImageId());
                    userDetailsEnt.addImageToUser(profileImageEnt);
                    profileImageEnt.setUserDetails(userDetailsEnt);
                    imageRepo.save(profileImageEnt);
                }else{
                    userDetailsEnt.addImageToUser(profileImageEnt);
                    profileImageEnt.setUserDetails(userDetailsEnt);
                }

            }

        }
        // add try catch
        //userDetailsRepo.save(userDetailsEnt);
        userRepo.save(userDetailsEnt.getUser());
        userDetailsRepo.save(userDetailsEnt);
        return true;
    }

//    public boolean activateUser(User user,String code){
//        Boolean isCodeEquals =  userDetailsRepo.findFirstByUserId(user.getUserId())
//                .getActivationCode().equals(code);
//        user.setActivated(isCodeEquals);
//        return isCodeEquals;
//    }

    public ProfileImageEnt toImageEntity(MultipartFile file) {
        ProfileImageEnt image = new ProfileImageEnt();
        image.setName(file.getName());
        image.setOriginalFileNAme(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        try {
            image.setBytes(file.getBytes());
        } catch (IOException e) {
            errorBuffer.append("Image upload error");
            return null;
        }
        return image;
    }

    public Boolean isUserActivated(Long userId){
        return userRepo.findFirstByUserId(userId).getActivated();
    }

    public boolean activateUser(String code){
        User user = findUserByActivationCode(code);
        if (user == null){

            return false;
        }
        user.setActivated(true);
        userRepo.save(user);
        UserDetailsEnt userDetailsEnt = userDetailsRepo.findFirstByUser(user);
        userDetailsEnt.setActivationCode(null);
        userDetailsRepo.save(userDetailsEnt);
        return true;
    }

    public UserDetailsEnt getUserDetailsByUser(User user){
        return userDetailsRepo.findFirstByUser(user);
    }

    public ProfileImageEnt findUserImageById(Long id){
        return imageRepo.findFirstByImageId(id);
    }

    public ProfileImageEnt findImageByUSerId(Long id){
        return userRepo.findFirstByUserId(id).getUserDetails().getImage();
    }

    public User findUserByActivationCode(String code){
        return userDetailsRepo.findFirstByActivationCode(code).getUser();
    }


    public void save(User user){
        userRepo.save(user);
    }

    public String findLoggedInUsername() {
        Object userDetails =
                SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails) userDetails).getUsername();
        }
        return null;
    }

    public User findByUsername(String username){
        return userRepo.findFirstByName(username);
    }

    public User findUserById(Long id){
        return userRepo.findFirstByUserId(id);
    }

//    public List<SiteUserDto> getAllusers(){
//        return userRepo.findAll().stream()
//                .map(this::toDto)
//                .collect(Collectors.toList());
//    }
//
//    private SiteUserDto toDto(User user){
//        return SiteUserDto.builder()
//                .username(user.getName())
//                .email(user.getEmail())
//                .build();
//    }

    public List<User> getAllUsersForAdmin(){
        return userRepo.findAllByRoleNot(UserRole.ADMIN);
    }

    public static String getAuth() {
        String authRole =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                        .stream()
                        .findFirst()
                        .get().toString()
                ;
        return authRole;
    }

}

package com.example.hobby.service.impl;

import com.example.hobby.exception.ObjectNotFoundException;
import com.example.hobby.model.dto.LoginDTO;
import com.example.hobby.model.dto.UserDTO;
import com.example.hobby.model.entity.User;
import com.example.hobby.model.entity.UserRole;
import com.example.hobby.model.entity.enums.UserRoleEnum;
import com.example.hobby.model.service.UserRegisterServiceModel;
import com.example.hobby.model.service.UserServiceModel;
import com.example.hobby.repository.UserRepository;
import com.example.hobby.repository.UserRoleRepository;
import com.example.hobby.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final HobbyUserServiceImpl hobbyUserService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, HobbyUserServiceImpl hobbyUserService, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.hobbyUserService = hobbyUserService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }


    @Override
    public User registerUser(UserRegisterServiceModel userServiceModel) {
        UserRole userRole = userRoleRepository.findByRole(UserRoleEnum.USER);
        User newUser = new User();
        newUser.setUsername(userServiceModel.getUsername());
        newUser.setFirstName(userServiceModel.getFirstName());
        newUser.setLastName(userServiceModel.getLastName());
        newUser.setEmail(userServiceModel.getEmail());
        newUser.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));
        newUser.setRoles(Set.of(userRole));
        newUser = userRepository.save(newUser);

        UserDetails principal = hobbyUserService.loadUserByUsername(newUser.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                newUser.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.
                getContext().
                setAuthentication(authentication);
        return newUser;
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElse(null);
    }

    @Override
    public void initAdmin() {
        if (userRepository.count() > 0) {
            return;
        }
        UserRole adminRole = userRoleRepository.findByRole(UserRoleEnum.ADMIN);
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("12345"));
        admin.setFirstName("Admin");
        admin.setLastName("Adminov");
        admin.setEmail("admin.adminov@abv.bg");

        admin.setRoles(Set.of(adminRole));
        userRepository.save(admin);
    }

    @Override
    public UserServiceModel findById(Long id) {
        return userRepository
                .findById(id)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }


    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::asUser).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public boolean checkIfUserExistsAndGoodCredential(LoginDTO loginDTO) {
         Optional<User> user = userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
         if(user.isPresent()){
             return true;
         }
         return false;


    }

    private UserDTO asUser(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

}

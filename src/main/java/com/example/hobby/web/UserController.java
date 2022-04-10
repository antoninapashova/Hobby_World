package com.example.hobby.web;

import com.example.hobby.model.binding.UserRegisterBindingModel;
import com.example.hobby.model.dto.HobbyDTO;
import com.example.hobby.model.dto.LoginDTO;
import com.example.hobby.model.dto.UserDTO;
import com.example.hobby.model.entity.User;
import com.example.hobby.model.service.UserRegisterServiceModel;
import com.example.hobby.service.UserService;
import com.example.hobby.service.impl.HobbyUserImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/register")
    public ResponseEntity<User> confirmRegister(@RequestBody UserRegisterBindingModel userRegisterBindingModel) {
        UserRegisterServiceModel serviceModel =
                modelMapper.map(userRegisterBindingModel, UserRegisterServiceModel.class);
        User user = userService.registerUser(serviceModel);
      return ResponseEntity.ok(user);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Optional<User>> getUserByUsername(@RequestBody String username) {

        Optional<User> user=userService.getUserByUsername(username);

        if (user==null) {
            return ResponseEntity.
                    notFound().
                    build();
        } else {
            return ResponseEntity.ok(user);
        }
    }
    @GetMapping("/profile")
    private ResponseEntity<User> profile( @AuthenticationPrincipal HobbyUserImpl user){
        User getLogedInUser = userService.getByUsername(user.getUsername());
        if(getLogedInUser==null){
            return ResponseEntity.
                    notFound().
                    build();
        }else{
            return ResponseEntity.ok(getLogedInUser);
        }
    }


    @PostMapping( value = "/login", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Optional<User>> login(@RequestBody LoginDTO loginDTO){
        boolean b = userService.checkIfUserExistsAndGoodCredential(loginDTO);
        if (b) {
            Optional<User> userByUsername = userService.getUserByUsername(loginDTO.getUsername());
            System.out.println("Login ready");
            return ResponseEntity.ok(userByUsername);
        } else {
            return ResponseEntity.
                    notFound().
                    build();
        }

    }

    @PostMapping("/login-error")
    public String error(){
        return "login-error";
    }
}

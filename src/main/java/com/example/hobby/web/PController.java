package com.example.hobby.web;

import com.example.hobby.model.dto.UserDTO;
import com.example.hobby.model.entity.User;
import com.example.hobby.model.view.HobbyViewModel;
import com.example.hobby.model.view.UserViewModel;
import com.example.hobby.service.HobbyService;
import com.example.hobby.service.UserService;
import com.example.hobby.service.impl.HobbyUserImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "http://localhost:4200")
public class PController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final HobbyService hobbyService;

    public PController(UserService userService, ModelMapper modelMapper, HobbyService hobbyService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.hobbyService = hobbyService;
    }


    @GetMapping("/userinfo")
    public String showUserHobbies(Model model, @AuthenticationPrincipal HobbyUserImpl user){
        List<HobbyViewModel> allHobbies = hobbyService.getAllHobbiesOfUser(user.getUserIdentifier()).stream()
                .map(hobbyServiceModel->modelMapper.map(hobbyServiceModel, HobbyViewModel.class))
                .collect(Collectors.toList());

        model.addAttribute("hobbies", allHobbies);
        return "hobbies";
    }



}

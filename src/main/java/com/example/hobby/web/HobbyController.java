package com.example.hobby.web;

import com.example.hobby.model.binding.HobbyAddBindingModel;
import com.example.hobby.model.dto.HobbyDTO;
import com.example.hobby.model.entity.Hobby;
import com.example.hobby.model.service.HobbyAddServiceModel;
import com.example.hobby.service.CloudinaryService;
import com.example.hobby.service.HobbyService;
import com.example.hobby.service.UserService;
import com.example.hobby.service.impl.HobbyUserImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/hobbies")
@CrossOrigin(origins = "http://localhost:4200")
public class HobbyController {

    private final HobbyService hobbyService;
    //private final CloudinaryService cloudinaryService;
    private final UserService userService;


    public HobbyController(HobbyService hobbyService,
                           //CloudinaryService cloudinaryService,
                           UserService userService) {
        this.hobbyService = hobbyService;
        //this.cloudinaryService = cloudinaryService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<HobbyDTO>> getAllHobbies(){
        List<HobbyDTO> allHobbies = hobbyService.getAllHobbies();
        return ResponseEntity.ok(allHobbies);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<HobbyDTO>> getHobbyByUsername(@PathVariable ("username") String username) {

        List<HobbyDTO> hobbies=hobbyService.getAllHobiesByUsername(username);

        if (hobbies.isEmpty()) {
            return ResponseEntity.
                    notFound().
                    build();
        } else {
            return ResponseEntity.ok(hobbies);
        }
    }

    @PostMapping("/hobby")
    public HobbyAddServiceModel addNewHobby(@RequestBody HobbyAddBindingModel hobbyAddBindingModel, @AuthenticationPrincipal HobbyUserImpl user ) throws IOException {
       return this.hobbyService.addHobby(hobbyAddBindingModel, user.getUserIdentifier());
    }



}

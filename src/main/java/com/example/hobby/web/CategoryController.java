package com.example.hobby.web;

import com.example.hobby.model.dto.HobbyDTO;
import com.example.hobby.service.CloudinaryService;
import com.example.hobby.service.HobbyService;
import com.example.hobby.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    private final HobbyService hobbyService;
    //private final CloudinaryService cloudinaryService;
    private final UserService userService;

    public CategoryController(HobbyService hobbyService,
                              //CloudinaryService cloudinaryService,
                              UserService userService) {
        this.hobbyService = hobbyService;
        //this.cloudinaryService = cloudinaryService;
        this.userService = userService;
    }

    @GetMapping("/biking")
    public ResponseEntity<List<HobbyDTO>> getHobbyByBikingCategory() {
        List<HobbyDTO> hobbies=hobbyService.getAllHobbiesByBikingCategory();
        if (hobbies.isEmpty()) {
            return ResponseEntity.
                    notFound().
                    build();
        } else {
            return ResponseEntity.ok(hobbies);
        }
    }

    @GetMapping("/photography")
    public ResponseEntity<List<HobbyDTO>> getHobbyByPhotographyCategory() {

        List<HobbyDTO> hobbies=hobbyService.getAllHobbiesByPhotographyCategory();

        if (hobbies.isEmpty()) {
            return ResponseEntity.
                    notFound().
                    build();
        } else {
            return ResponseEntity.ok(hobbies);
        }
    }

    @GetMapping("/flowers")
    public ResponseEntity<List<HobbyDTO>> getHobbyByFlowersCategory() {

        List<HobbyDTO> hobbies=hobbyService.getAllHobbiesByFlowersCategory();

        if (hobbies.isEmpty()) {
            return ResponseEntity.
                    notFound().
                    build();
        } else {
            return ResponseEntity.ok(hobbies);
        }
    }

    @GetMapping("/painting")
    public ResponseEntity<List<HobbyDTO>> getHobbyByPaintingCategory() {

        List<HobbyDTO> hobbies=hobbyService.getAllHobbiesByPaintingCategory();

        if (hobbies.isEmpty()) {
            return ResponseEntity.
                    notFound().
                    build();
        } else {
            return ResponseEntity.ok(hobbies);
        }
    }
}

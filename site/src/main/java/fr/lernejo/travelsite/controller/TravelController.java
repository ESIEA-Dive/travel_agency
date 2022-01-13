package fr.lernejo.travelsite.controller;

import fr.lernejo.travelsite.model.User;
import fr.lernejo.travelsite.service.TravelSiteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TravelController {

    private TravelSiteService travelSiteService;

    public TravelController(TravelSiteService travelSiteService) {
        this.travelSiteService = travelSiteService;
    }

    @PostMapping(value = "/inscription")
    public User inscription(@RequestBody User user) {
        travelSiteService.addUser(user);
        return user;
    }


}

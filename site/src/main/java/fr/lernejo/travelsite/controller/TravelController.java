package fr.lernejo.travelsite.controller;

import fr.lernejo.travelsite.model.Travel;
import fr.lernejo.travelsite.model.User;
import fr.lernejo.travelsite.service.TravelSiteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TravelController {

    private final TravelSiteService travelSiteService;

    public TravelController(TravelSiteService travelSiteService) {
        this.travelSiteService = travelSiteService;
    }

    @PostMapping(value = "/inscription")
    public User inscription(@RequestBody User user) {
        travelSiteService.addUser(user);
        return user;
    }

    @ResponseBody
    @GetMapping("/travels")
    public List<Travel> findTravelsByUsername(@RequestParam String userName){
        return travelSiteService.findTravelsByUsername(userName);
    }

}

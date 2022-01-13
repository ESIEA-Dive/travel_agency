package fr.lernejo.travelsite.service;

import fr.lernejo.travelsite.model.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TravelSiteService {
    private final Set<User> users = new HashSet<>();

    public void addUser(User user) {
        users.stream().filter(user1 -> user1.userName().equalsIgnoreCase(user.userName())).findFirst()
            .ifPresent(users::remove);
        users.add(user);
    }
}

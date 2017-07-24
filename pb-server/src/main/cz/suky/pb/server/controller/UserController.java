package cz.suky.pb.server.controller;

import cz.suky.pb.server.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * User controller
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("userId") Long userId) {
        User user = new User();
        user.setId(userId);
        user.setUsername("test");
        return ResponseEntity.ok(user);
    }
}

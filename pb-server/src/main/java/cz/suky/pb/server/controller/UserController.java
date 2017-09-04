package cz.suky.pb.server.controller;

import cz.suky.pb.server.domain.User;
import cz.suky.pb.server.dto.LoginRequest;
import cz.suky.pb.server.exception.UserException;
import cz.suky.pb.server.repository.UserRepository;
import cz.suky.pb.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * User controller
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.getUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (user == null) {
            throw UserException.notAuthorized();
        }
        return ResponseEntity.ok(user);
    }
}

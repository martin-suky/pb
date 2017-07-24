package cz.suky.pb.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Created by none_ on 16-Jul-17.
 */
@RestController
@RequestMapping("/api/test")
public class TestController {
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<LocalDateTime> test() {
        return ResponseEntity.ok(LocalDateTime.now());
    }
}

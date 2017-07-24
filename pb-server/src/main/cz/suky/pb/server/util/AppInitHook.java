package cz.suky.pb.server.util;

import cz.suky.pb.server.domain.User;
import cz.suky.pb.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * Created by none_ on 16-Jul-17.
 */
@Service
public class AppInitHook implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        User user = new User();
        user.setUsername("none_");
        user.setPassword("Password1");
        userRepository.save(user);
    }
}

package cz.suky.pb.server.util;

import cz.suky.pb.server.domain.ConfigKey;
import cz.suky.pb.server.domain.Configuration;
import cz.suky.pb.server.repository.ConfigurationRepository;
import cz.suky.pb.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by none_ on 16-Jul-17.
 */
@Service
@Transactional
public class AppInitHook implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        Configuration initialized = configurationRepository.findByKey(ConfigKey.APP_INITIALIZED)
                .orElse(new Configuration(ConfigKey.APP_INITIALIZED));

        if (initialized.getValueAsBoolean() == false) {
            userService.registerUser("none_", "Password1");
            initialized.setValueAsBoolean(true);
            configurationRepository.save(initialized);
        }
    }
}

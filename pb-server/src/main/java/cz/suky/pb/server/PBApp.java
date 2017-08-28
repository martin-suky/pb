package cz.suky.pb.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Created by none_ on 10/31/16.
 */
@SpringBootApplication
public class PBApp {

    public static void main(String[] args) {
        SpringApplication.run(PBApp.class, args);
    }
}

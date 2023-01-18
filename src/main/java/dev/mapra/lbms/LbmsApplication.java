package dev.mapra.lbms;

import dev.mapra.lbms.model.Admin;
import dev.mapra.lbms.model.Book;
import dev.mapra.lbms.model.Publisher;
import dev.mapra.lbms.model.Writer;
import dev.mapra.lbms.services.AdminService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;

@SpringBootApplication
public class LbmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LbmsApplication.class, args);
    }

//    @Bean
    CommandLineRunner commandLineRunner (AdminService adminService) {
        return args -> {
            adminService.saveAdmin(new Admin(null,"user1","1234",new ArrayList<>(),new ArrayList<>(),new ArrayList<>()));
            adminService.saveAdmin(new Admin(null,"user2","1234",new ArrayList<>(),new ArrayList<>(),new ArrayList<>()));
            adminService.saveAdmin(new Admin(null,"user3","1234",new ArrayList<>(),new ArrayList<>(),new ArrayList<>()));
        };
    }

    @Bean
    PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

}

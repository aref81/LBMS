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
//    CommandLineRunner commandLineRunner (AdminService adminService) {
//        return args -> {
//            adminService.saveAdmin(new Admin(null,"user1","1234",new ArrayList<>(),new ArrayList<>(),new ArrayList<>()));
//            adminService.saveAdmin(new Admin(null,"user2","1234",new ArrayList<>(),new ArrayList<>(),new ArrayList<>()));
//            adminService.saveAdmin(new Admin(null,"user3","1234",new ArrayList<>(),new ArrayList<>(),new ArrayList<>()));
//
//            ArrayList<Publisher> publishers = new ArrayList<>();
//            publishers.add(adminService.savePublisher(new Publisher(null,"publisher1","2017",new ArrayList<>(),adminService.getAdmin("user1"))));
//            publishers.add(adminService.savePublisher(new Publisher(null,"publisher2","2017",new ArrayList<>(),adminService.getAdmin("user2"))));
//            publishers.add(adminService.savePublisher(new Publisher(null,"publisher3","2017",new ArrayList<>(),adminService.getAdmin("user3"))));
//
//            ArrayList<Writer> writers = new ArrayList<>();
//            writers.add(adminService.saveWriter(new Writer(null,"name1","family1", LocalDateTime.now(),new ArrayList<>(),adminService.getAdmin("user1"))));
//            writers.add(adminService.saveWriter(new Writer(null,"name2","family2", LocalDateTime.now(),new ArrayList<>(),adminService.getAdmin("user1"))));
//            writers.add(adminService.saveWriter(new Writer(null,"name3","family3", LocalDateTime.now(),new ArrayList<>(),adminService.getAdmin("user1"))));
//
//            adminService.saveBook(new Book(null,"book","2018", 9L,writers,publishers.get(1),adminService.getAdmin("user1")));
//        };
//    }

    @Bean
    PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

}

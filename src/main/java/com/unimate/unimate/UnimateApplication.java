package com.unimate.unimate;

import com.unimate.unimate.entity.Account;
import com.unimate.unimate.entity.Role;
import com.unimate.unimate.enums.AccountStatusEnum;
import com.unimate.unimate.enums.RoleEnum;
import com.unimate.unimate.repository.RoleRepository;
import com.unimate.unimate.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan("com.unimate.*")
@ComponentScan(basePackages = "com.unimate.unimate.*")
public class UnimateApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnimateApplication.class, args);
    }

    @Bean
    @Transactional
    CommandLineRunner run(AccountService accountService, RoleRepository roleRepository) {
        return args -> {
            Role studentRole = new Role();
            studentRole.setName(RoleEnum.STUDENT);
            roleRepository.save(studentRole);

            Role adminRole = new Role();
            adminRole.setName(RoleEnum.ADMIN);
            roleRepository.save(adminRole);

            Role teacherRole = new Role();
            teacherRole.setName(RoleEnum.TEACHER);
            roleRepository.save(teacherRole);

            Role topLevel = new Role();
            topLevel.setName(RoleEnum.TOP_LEVEL);
            roleRepository.save(topLevel);

            Account account = new Account();
            account.setName("Bintang");
            account.setEmail("bintang@gmail.com");
            account.setPassword("12345");
            account.setProfilePicture("pic");
            account.setRole(studentRole);
            account.setStatus(AccountStatusEnum.VERIFIED);

            accountService.saveAccount(account);
        };
    }
}

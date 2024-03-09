package com.unimate.unimate;

import com.unimate.unimate.entity.Account;
import com.unimate.unimate.entity.Role;
import com.unimate.unimate.enums.AccountStatusEnum;
import com.unimate.unimate.enums.RoleEnum;
import com.unimate.unimate.repository.RoleRepository;
import com.unimate.unimate.service.AccountService;
import com.unimate.unimate.service.AuthenticationService;

import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCrypt;

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
    CommandLineRunner run(AccountService accountService, RoleRepository roleRepository, AuthenticationService authenticationService) {
        return args -> {
            authenticationService.starter();

            Account account = new Account();
            account.setName("Bintang");
            account.setEmail("bintang@gmail.com");
            account.setPassword(BCrypt.hashpw("12345", BCrypt.gensalt()));
            account.setProfilePicture("pic");
            account.setRole(roleRepository.findRoleByName(RoleEnum.STUDENT));
            account.setStatus(AccountStatusEnum.VERIFIED);

            accountService.saveAccount(account);
        };
    }
}
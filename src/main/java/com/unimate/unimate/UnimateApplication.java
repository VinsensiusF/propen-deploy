package com.unimate.unimate;

import com.unimate.unimate.dto.SignUpDTO;
import com.unimate.unimate.entity.Role;
import com.unimate.unimate.enums.RoleEnum;
import com.unimate.unimate.repository.RoleRepository;
import com.unimate.unimate.service.AccountService;
import com.unimate.unimate.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan("com.unimate.*")
@ComponentScan(basePackages = "com.unimate.unimate.*")
@ConfigurationPropertiesScan(basePackages = {"com.unimate.unimate.config"})
public class UnimateApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationService authenticationService;

    public static void main(String[] args) {
        SpringApplication.run(UnimateApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            for (RoleEnum roleName : RoleEnum.values()) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }
        if(authenticationService.findAll().isEmpty()){
            SignUpDTO signUpDTOAdmin = new SignUpDTO("admin@gmail.com", "admin", "admin");
            SignUpDTO signUpDTOTopLevel = new SignUpDTO("toplevel@gmail.com", "toplevel", "toplevel");
            SignUpDTO signUpDTOTeacher = new SignUpDTO("teacher@gmail.com", "teacher", "teacher");
            SignUpDTO signUpDTOCS = new SignUpDTO("cs@gmail.com", "cs", "cs");
            authenticationService.initialSignUp(signUpDTOAdmin);
            authenticationService.initialSignUp(signUpDTOTeacher);
            authenticationService.initialSignUp(signUpDTOTopLevel);
            authenticationService.initialSignUp(signUpDTOCS);
        }
    }
}

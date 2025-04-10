package net.haji.springmvcthymeleafsecured;

import net.haji.springmvcthymeleafsecured.dao.entities.Patient;
import net.haji.springmvcthymeleafsecured.dao.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SpringMvcThymeleafApplicationSecured {

    public static void main(String[] args) {
        SpringApplication.run(SpringMvcThymeleafApplicationSecured.class, args);
    }

    //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository) {
        return args -> {
            patientRepository.save(Patient.builder().name("Ahmad").sick(false).birthDate(new Date()).score(20).build());
            patientRepository.save(Patient.builder().name("Mohammed").sick(false).birthDate(new Date()).score(20).build());
            patientRepository.save(Patient.builder().name("Hamid").sick(true).birthDate(new Date()).score(20).build());
            patientRepository.save(Patient.builder().name("Moustafa").sick(true).birthDate(new Date()).score(20).build());
            patientRepository.save(Patient.builder().name("Yasser").sick(true).birthDate(new Date()).score(20).build());
            patientRepository.save(Patient.builder().name("Omar").sick(false).birthDate(new Date()).score(20).build());
            patientRepository.save(Patient.builder().name("Zakaria").sick(false).birthDate(new Date()).score(20).build());
            patientRepository.save(Patient.builder().name("Mourad").sick(false).birthDate(new Date()).score(20).build());
            patientRepository.save(Patient.builder().name("Mourad").sick(false).birthDate(new Date()).score(20).build());
            patientRepository.save(Patient.builder().name("Mourad").sick(false).birthDate(new Date()).score(20).build());

            patientRepository.findAll().forEach(System.out::println);

            System.out.println("**************************************\n\n\n");
            System.out.println("Testing the find by name contains : ");
            List<Patient> patients = patientRepository.findByNameContainsIgnoreCase("y");
            patients.forEach(System.out::println);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

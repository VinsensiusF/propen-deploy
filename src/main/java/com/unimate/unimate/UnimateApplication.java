package com.unimate.unimate;

import com.unimate.unimate.entity.*;
import com.unimate.unimate.service.*;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan("com.unimate.*")
@ComponentScan(basePackages = "com.unimate.unimate.*")
@ConfigurationPropertiesScan(basePackages = {"com.unimate.unimate.config"})
public class UnimateApplication {


    public static void main(String[] args) {
        SpringApplication.run(UnimateApplication.class, args);
    }

    @Bean
    @Transactional
    CommandLineRunner run(AuthenticationService authenticationService, KelasService kelasService, CourseService courseService, UjianService ujianService, QuestionService questionService, KelasSiswaService kelasSiswaService){
        return args -> {
            Kelas kelas = new Kelas();
            kelas.setName("Kelas Matematika");
            kelas.setRating(4f);
            kelas.setCategory("Persiapan Tes");
            kelas.setDescription("Kelas yang susah");
            kelas.setBenefits(new ArrayList<>(Arrays.asList("Pintar", "Kaya", "Keren")));
            kelas.setSyllabuses(new ArrayList<>(Arrays.asList("Aljabar", "Statistik", "Geometri")));
            kelas.setPrice(15000L);
            kelasService.saveKelas(kelas);
            Kelas kelas2 = new Kelas();
            kelas2.setName("Kelas Fisika");
            kelas2.setRating(4f);
            kelas2.setCategory("Pelajaran");
            kelas2.setDescription("Kelas yang gampang");
            kelas2.setBenefits(new ArrayList<>(Arrays.asList("Pintar", "Kaya", "Keren")));
            kelas2.setSyllabuses(new ArrayList<>(Arrays.asList("Klasik", "Termofisika", "Astrofisika")));
            kelas2.setPrice(15000L);
            kelasService.saveKelas(kelas2);

            Ujian ujian = new Ujian();
            ujian.setKelas(kelas);
            ujian.setDuration(10000L);
            ujian.setTitle("Ujian Fisika");
            ujian.setStartDate(LocalDateTime.of(2024, 2, 3, 10, 5));
            ujian.setEndDate(LocalDateTime.of(2024, 2, 4, 10, 5));
            ujian.setPassingGrade(50.0);
            ujianService.saveUjian(ujian);

            Ujian ujian3 = new Ujian();
            ujian3.setKelas(kelas);
            ujian3.setDuration(10000L);
            ujian3.setTitle("Ujian Inggris");
            ujian3.setStartDate(LocalDateTime.of(2024, 2, 3, 10, 5));
            ujian3.setEndDate(LocalDateTime.of(2024, 2, 4, 10, 5));
            ujian3.setPassingGrade(50.0);
            ujianService.saveUjian(ujian3);

            Ujian ujian2 = new Ujian();
            ujian2.setKelas(kelas2);
            ujian2.setDuration(10000L);
            ujian2.setTitle("Ujian Fisika");
            ujian2.setStartDate(LocalDateTime.of(2024, 2, 3, 10, 5));
            ujian2.setEndDate(LocalDateTime.of(2024, 2, 4, 10, 5));
            ujian2.setPassingGrade(50.0);
            ujianService.saveUjian(ujian2);

            QuestionContent question1 = new QuestionContent();
            question1.setQuestionSentence("Siapa penemu teori relativitas");
            question1.setQuestionText("Teori relativitas, teori kerelatifan, atau teori kenisbian merupakan teori yang membahas mengenai kecepatan dan percepatan yang diukur secara berbeda melalui kerangka acuan.");
            question1.setA("Isaac Newton");
            question1.setB("Albert Einstein");
            question1.setC("J. Robert Oppenheimer");
            question1.setD("Nicola Tesla");
            question1.setCorrectAnswer("Albert Einstein");
            question1.setUjian(ujian);
            questionService.saveQuesionContent(question1);

            QuestionContent question2 = new QuestionContent();
            question2.setQuestionSentence("Planet terpanas pada sistem tata surya?");
            question2.setQuestionText("Planet, siarah, kaukab, pehu (Bahasa Batak kuno) atau greha (bahasa Jawa kuno) adalah benda astronomi yang mengorbit sebuah bintang atau sisa bintang yang cukup besar untuk memiliki gravitasi sendiri.");
            question2.setA("Bumi");
            question2.setB("Venus");
            question2.setC("Merkurius");
            question2.setD("Mars");
            question2.setCorrectAnswer("Venus");
            question2.setUjian(ujian);
            questionService.saveQuesionContent(question2);

            QuestionContent question3 = new QuestionContent();
            question3.setQuestionSentence("Unsur dengan elektronegativitas tertinggi?");
            question3.setA("Oksigen");
            question3.setB("Xenon");
            question3.setC("Fluor");
            question3.setD("Helium");
            question3.setCorrectAnswer("Fluor");
            question3.setUjian(ujian);
            questionService.saveQuesionContent(question3);

            QuestionContent question4 = new QuestionContent();
            question4.setQuestionSentence("Nama presiden pertama Indonesia?");
            question4.setA("Jokowi");
            question4.setB("Gusdur");
            question4.setC("Prabowo");
            question4.setD("Soekarno");
            question4.setCorrectAnswer("Soekarno");
            question4.setUjian(ujian2);
            questionService.saveQuesionContent(question4);

            QuestionContent question5 = new QuestionContent();
            question5.setQuestionSentence("What is the color of the sky?");
            question5.setA("Blue");
            question5.setB("Green");
            question5.setC("Violet");
            question5.setD("Rainbow");
            question5.setCorrectAnswer("Blue");
            question5.setUjian(ujian3);
            questionService.saveQuesionContent(question5);

            QuestionContent question6 = new QuestionContent();
            question6.setQuestionSentence("What's 1+1?");
            question6.setA("1");
            question6.setB("2");
            question6.setC("3");
            question6.setD("4");
            question6.setCorrectAnswer("2");
            question6.setUjian(ujian3);
            questionService.saveQuesionContent(question6);

            Course course1 = new Course();
            course1.setName("Course 1");
            course1.setKelas(kelas);
            Course course2 = new Course();
            course2.setName("Course 2");
            course2.setKelas(kelas);
            Course course3 = new Course();
            course3.setName("Course 3");
            course3.setKelas(kelas2);
            courseService.saveCourse(course1);
            courseService.saveCourse(course2);
            courseService.saveCourse(course3);

//
            CourseContent courseContent1 = new CourseContent();
            courseContent1.setType(1);
            courseContent1.setLink("www.course1.com/content1");
            courseContent1.setName("Zoom");
            courseContent1.setDescription("First content of Course 1");
            courseContent1.setCourse(course1);
            CourseContent courseContent2 = new CourseContent();
            courseContent2.setType(2);
            courseContent2.setLink("www.course1.com/content2");
            courseContent2.setName("Quiz");
            courseContent2.setDescription("Second content of Course 1");
            courseContent2.setCourse(course1);
            CourseContent courseContent3 = new CourseContent();
            courseContent3.setType(1);
            courseContent3.setLink("www.course2.com/content1");
            courseContent3.setName("Zoom");
            courseContent3.setDescription("First content of Course 2");
            courseContent3.setCourse(course2);
            CourseContent courseContent4 = new CourseContent();
            courseContent4.setType(1);
            courseContent4.setLink("www.course2.com/content1");
            courseContent4.setName("Zoom");
            courseContent4.setDescription("First content of Course 3");
            courseContent4.setCourse(course3);
            courseService.saveCourseContent(courseContent1);
            courseService.saveCourseContent(courseContent2);
            courseService.saveCourseContent(courseContent3);
            courseService.saveCourseContent(courseContent4);


            authenticationService.starter();
//            kelasSiswaService.enrollStudent(5L, 1L);
//            kelasSiswaService.enrollStudent(5L, 2L);
        };
    }
}
package br.com.pillwatcher.dpb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DpbApplication {

    public static void main(String[] args) {
        SpringApplication.run(DpbApplication.class, args);
    }

}

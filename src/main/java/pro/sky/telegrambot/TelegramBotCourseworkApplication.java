package pro.sky.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TelegramBotCourseworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotCourseworkApplication.class, args);
    }

}

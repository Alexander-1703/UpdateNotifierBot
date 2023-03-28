package ru.tinkoff.edu.java.bot.telegrambot.scheduler;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.tinkoff.edu.java.bot.configuration.ApplicationConfig;

@Configuration
public class SchedulerUpdatesConfig {
    @Bean
    public Duration updatesScheduler(ApplicationConfig appConfig) {
        return appConfig.fetchUpdates().fixedDelay();
    }
}

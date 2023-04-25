package ru.tinkoff.edu.java.scrapper.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.tinkoff.edu.java.scrapper.service.interfaces.LinkUpdater;

@Component
@Slf4j
@RequiredArgsConstructor
public class LinkUpdateScheduler {
    private final LinkUpdater linkUpdater;

    @Scheduled(fixedDelayString = "#{@scheduler}")
    public void update() {
        log.info("Updating links...");
        linkUpdater.update();
    }
}

package ru.tinkoff.edu.java.scrapper.service.jooq;

import java.net.URI;
import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqLinkChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

@Slf4j
@RequiredArgsConstructor
public class JooqLinkService implements LinkService {
    private final JooqLinkRepository linkRepository;
    private final JooqLinkChatRepository subscription;
    private final JooqLinkUpdater linkUpdater;

    @Override
    @Transactional
    public Link add(long tgChatId, URI url) {
        Link link = linkRepository.findByLink(url.toString());
        if (link == null) {
            link = new Link();
            link.setLink(url.toString());
            link = linkRepository.save(link);
            log.info("add link: " + link.getLink());

            try {
                Link updatedLink = linkUpdater.update(link).link();
                linkRepository.save(updatedLink);

            } catch (NullPointerException e) {
                log.error("first updating error: " + url);
            }
        }
        subscription.addLinkToChat(link.getId(), tgChatId);
        log.info("bind link " + link.getLink() + " to user " + tgChatId);
        return link;
    }

    @Override
    @Transactional
    public Link remove(long tgChatId, URI url) {
        Link link = linkRepository.findByLink(url.toString());
        subscription.removeLinkFromChat(link.getId(), tgChatId);
        log.info("unbind link " + link.getLink() + " from " + tgChatId);
        if (subscription.findChatsByLinkId(link.getId()).isEmpty()) {
            linkRepository.remove(link.getId());
            log.info("remove link: " + link.getLink());
        }
        return link;
    }

    @Override
    @Transactional
    public Collection<Link> listAll(long tgChatId) {
        return subscription.findLinksByChatId(tgChatId);
    }
}

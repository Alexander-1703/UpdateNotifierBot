package ru.tinkoff.edu.java.bot.telegrambot.wrapper.commands;

import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import lombok.extern.slf4j.Slf4j;
import ru.tinkoff.edu.java.bot.enums.CommandEnum;

@Slf4j
@Component
public class UntrackCommand implements Command {
    private static final String UNTRACK_MESSAGE = """
            Ссылка удалена из списка отслеживания.
            Вы больше не будете получать уведомления об изменениях
            """;

    @Override
    public String getCommand() {
        return CommandEnum.UNTRACK.getCommandName();
    }

    @Override
    public String getDescription() {
        return CommandEnum.UNTRACK.getDescription();
    }

    @Override
    public SendMessage handle(Update update) {
        log.info("link tracking end...");
        return new SendMessage(update.message().chat().id(), UNTRACK_MESSAGE);
    }
}
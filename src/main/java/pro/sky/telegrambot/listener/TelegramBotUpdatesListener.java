package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.service.NotificationService;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    private final NotificationService notificationService;

    public TelegramBotUpdatesListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public int process(List<Update> updates) {
        updates.stream()
                .filter(update -> update.message() != null)
                .filter(update -> update.message().text() != null)
                .forEach(this::processUpdate);
        logger.info("Processing update: {}", updates);

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void processUpdate(Update update) {
        String userMessage = update.message().text();
        Long chatId = update.message().chat().id();
        if (userMessage.equals("/start")) {
            telegramBot.execute(new SendMessage(chatId, "Приветсвую в моем боте! Для внесения задачи введите её в формате '01.01.2023 12:10 Сходить за покупками'"));
            logger.info("Response received /start");
        } else {
            if (this.notificationService.processNotification(chatId, userMessage)) {
                this.telegramBot.execute(new SendMessage(chatId, "Задача создана!"));
                logger.info("Response received: 'Задача создана!'");
            } else {
                this.telegramBot.execute(new SendMessage(chatId, "Формат для внесения задачи: '01.01.2023 12:10 Сходить за покупками'"));
            }
        }
    }
}
package pro.sky.telegrambot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.DeleteMyCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * configurations for setting Telegram - bot
 */
@Configuration
public class TelegramBotConfiguration {


    /**
     * The Telegram bot token value read from the configuration.
     */
    @Value("${telegram.bot.token}")
    private String token;

    /**
     * Creates and configures a TelegramBot instance using a token.
     *
     * @return Telegram - bot
     */
    @Bean
    public TelegramBot telegramBot() {
        TelegramBot bot = new TelegramBot(token);
        bot.execute(new DeleteMyCommands());
        return bot;
    }

}

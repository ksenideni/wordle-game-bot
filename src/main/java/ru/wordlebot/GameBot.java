package ru.wordlebot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendGame;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.validation.constraints.NotNull;

public class GameBot extends TelegramLongPollingBot {

    private final static String BOT_USERNAME = "Wordle";
    private final static String START_COMMAND = "/start";
    private final static String GAME_SHORT_NAME = "wordle";
    private final GameInviteUrlBuilder gameInviteUrlBuilder;

    public GameBot(String token, GameInviteUrlBuilder gameInviteUrlBuilder) {
        super(token);
        this.gameInviteUrlBuilder = gameInviteUrlBuilder;
    }

    public void onUpdateReceived(@NotNull Update update) {
        if (shouldInviteInGameOnUpdate(update)) {
            sendGame(update.getMessage().getChat());
        } else if (shouldRedirectToGameOnUpdate(update)) {
            redirectToGame(update.getCallbackQuery());
        }
    }

    private boolean shouldInviteInGameOnUpdate(@NotNull Update update) {
        return update.hasMessage() && update.getMessage().getEntities()
                .stream()
                .filter(e -> e.getType().equals(EntityType.BOTCOMMAND))
                .anyMatch(e -> update.getMessage().getText().substring(e.getOffset(), e.getLength()).equals(START_COMMAND));
    }

    private boolean shouldRedirectToGameOnUpdate(@NotNull Update update) {
        return update.hasCallbackQuery() && update.getCallbackQuery().getGameShortName().equals(GAME_SHORT_NAME);
    }

    private void sendGame(@NotNull Chat chat) {
        SendGame sendGameMethod = SendGame.builder()
                .chatId(chat.getId())
                .gameShortName(GAME_SHORT_NAME)
                .build();
        tryExecute(sendGameMethod);
    }

    private void redirectToGame(@NotNull CallbackQuery callbackQuery) {
        AnswerCallbackQuery answerCallbackQuery = AnswerCallbackQuery.builder()
                .callbackQueryId(callbackQuery.getId())
                .url(gameInviteUrlBuilder.build(callbackQuery))
                .build();
        tryExecute(answerCallbackQuery);
    }

    private void tryExecute(@NotNull BotApiMethod<?> botApiMethod) {
        try {
            execute(botApiMethod);
        } catch (TelegramApiException e) {
            System.err.println(e.getMessage());
        }
    }

    public String getBotUsername() {
        return BOT_USERNAME;
    }
}

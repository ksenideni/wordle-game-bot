package ru.wordlebot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

    private final static String TOKEN_KEY = "TOKEN_KEY";
    private final static  String BASE_GAME_URL_KEY = "BASE_GAME_URL_KEY";

    public static void main(String[] args) {

        try {
            String token = TOKEN_KEY;
            String gameBaseUrl = BASE_GAME_URL_KEY;
            GameInviteUrlBuilder gameInviteUrlBuilder = new SimpleGameInviteUrlBuilder(gameBaseUrl);
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new GameBot(token, gameInviteUrlBuilder));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}

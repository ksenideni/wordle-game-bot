package ru.wordlebot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

    private final static String TOKEN_KEY = "tg_token";
    private final static  String BASE_GAME_URL_KEY = "localhost:8080";

    public static void main(String[] args) {

        try {
            String token = System.getenv(TOKEN_KEY);
            String gameBaseUrl = System.getenv(BASE_GAME_URL_KEY);
            GameInviteUrlBuilder gameInviteUrlBuilder = new SimpleGameInviteUrlBuilder(gameBaseUrl);
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new GameBot(token, gameInviteUrlBuilder));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}

package ru.wordlebot;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import javax.validation.constraints.NotNull;

public class SimpleGameInviteUrlBuilder implements GameInviteUrlBuilder {

    private final String gameBaseUrl;

    public SimpleGameInviteUrlBuilder(String gameBaseUrl) {
        this.gameBaseUrl = gameBaseUrl;
    }

    @Override
    @NotNull
    public String build(@NotNull CallbackQuery callbackQuery) {
        return String.format("%s?chat_id=%s&user_id=%d",
                gameBaseUrl,
                callbackQuery.getChatInstance(),
                callbackQuery.getFrom().getId());
    }
}

package ru.wordlebot;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface GameInviteUrlBuilder {

    String build(CallbackQuery callbackQuery);

}

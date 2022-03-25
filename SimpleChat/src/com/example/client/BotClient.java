package com.example.client;

import com.example.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Бот, представляющий собой клиента, который автоматически отвечает на запросы о предоставления времени
 * в разных форматах.
 */
public class BotClient extends Client {

    public class BotSocketThread extends SocketThread {
        protected void processIncomingMessage(String message) {
            // Выводим текст сообщения в консоль
            ConsoleHelper.writeMessage(message);
            // Отделяем отправителя от текста сообщения
            String[] elements = message.split(": ");
            if (elements.length != 2) return;
            String userName = elements[0].trim();
            String messageWithoutUserName = elements[1].trim();

            // Подготавливаем формат для отправки даты согласно запросу
            String format = null;
            switch (messageWithoutUserName) {
                case "дата":
                    format = "d.MM.YYYY";
                    break;
                case "день":
                    format = "d";
                    break;
                case "месяц":
                    format = "MMMM";
                    break;
                case "год":
                    format = "YYYY";
                    break;
                case "время":
                    format = "H:mm:ss";
                    break;
                case "час":
                    format = "H";
                    break;
                case "минуты":
                    format = "m";
                    break;
                case "секунды":
                    format = "s";
                    break;
            }

            if (format != null) {
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                String answer = String.format("Информация для %s: %s",
                        userName, formatter.format(new GregorianCalendar().getTime()));
                BotClient.this.sendTextMessage(answer);
            }
        }

        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }
    }

    protected String getUserName() {
        int x = (int) (Math.random() * 100);
        return "date_bot_" + x;
    }

    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    public static void main(String[] args) {
        BotClient botClient = new BotClient();
        botClient.run();
    }
}

package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BotClient extends Client {

    public static void main(String[] args) {
        new BotClient().run();
    }

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected String getUserName() {
        return "date_bot_" + (int) (Math.random() * 100);
    }

    public class BotSocketThread extends SocketThread {
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
            if (!message.contains(": ")) return;
            String sender = message.split(": ")[0];
            String messageText = message.split(": ")[1];
            SimpleDateFormat format = null;
            switch (messageText) {
                case "дата":
                    format = new SimpleDateFormat("d.MM.yyyy");
                    break;
                case "день":
                    format = new SimpleDateFormat("d");
                    break;
                case "месяц":
                    format = new SimpleDateFormat("MMMM");
                    break;
                case "год":
                    format = new SimpleDateFormat("yyyy");
                    break;
                case "время":
                    format = new SimpleDateFormat("H:mm:ss");
                    break;
                case "час":
                    format = new SimpleDateFormat("H");
                    break;
                case "минуты":
                    format = new SimpleDateFormat("m");
                    break;
                case "секунды":
                    format = new SimpleDateFormat("s");
                    break;
                }
                if (format != null)
                    sendTextMessage("Информация для " + sender + ": " + format.format(Calendar.getInstance().getTime()));
        }
    }
}

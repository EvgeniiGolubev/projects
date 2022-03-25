package com.example.client;

import com.example.*;

import java.io.IOException;
import java.net.Socket;

/**
 * Клиент, в начале своей работы, должен запросить у пользователя адрес и порт сервера,
 * подсоединиться к указанному адресу, получить запрос имени от сервера, спросить имя у пользователя,
 * отправить имя пользователя серверу, дождаться принятия имени сервером.
 * После этого клиент может обмениваться текстовыми сообщениями с сервером.
 * Обмен сообщениями будет происходить в двух параллельно работающих потоках.
 * Один будет заниматься чтением из консоли и отправкой прочитанного серверу,
 * а второй поток будет получать данные от сервера и выводить их в консоль.
 */
public class Client {
    protected Connection connection;
    private volatile boolean clientConnected = false;

    // SocketThread отвечать за поток, устанавливающий сокетное соединение и читающий сообщения сервера.
    public class SocketThread extends Thread {

        protected void processIncomingMessage(String message) {
            // Выводим текст сообщения в консоль
            ConsoleHelper.writeMessage(message);
        }

        protected void informAboutAddingNewUser(String userName) {
            // Выводим информацию о добавлении участника
            ConsoleHelper.writeMessage("Участник '" + userName + "' присоединился к чату.");
        }

        protected void informAboutDeletingNewUser(String userName) {
            // Выводим информацию о выходе участника
            ConsoleHelper.writeMessage("Участник '" + userName + "' покинул чат.");
        }

        protected void notifyConnectionStatusChanged(boolean clientConnected) {
            /*
            Этот метод устанавливает значение поля clientConnected внешнего объекта Client,
            оповещает (пробуждает ожидающий) основной поток класса Client.
             */
            Client.this.clientConnected = clientConnected;
            synchronized (Client.this) {
                Client.this.notify();
            }
        }

        // Этот метод представляет клиента серверу.
        protected void clientHandshake() throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.NAME_REQUEST) { // Сервер запросил имя пользователя
                    String userName = getUserName(); // Запрашиваем ввод имени с консоли
                    connection.send(new Message(MessageType.USER_NAME, userName)); // Отправляем имя на сервер
                } else if (message.getType() == MessageType.NAME_ACCEPTED) { // Сервер принял имя пользователя
                    notifyConnectionStatusChanged(true); // Сообщаем главному потоку, что он может продолжить работу
                    return;
                } else {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }

        // Этот метод реализует главный цикл обработки сообщений сервера.
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.TEXT) { // Сервер прислал сообщение с текстом
                    processIncomingMessage(message.getData());
                } else if (message.getType() == MessageType.USER_ADDED) {
                    informAboutAddingNewUser(message.getData());
                } else if (message.getType() == MessageType.USER_REMOVED) {
                    informAboutDeletingNewUser(message.getData());
                } else {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }

        @Override
        public void run() {
            try {
                // Создаем соединение с сервером
                connection = new Connection(new Socket(getServerAddress(), getServerPort()));
                clientHandshake();
                clientMainLoop();
            } catch (IOException | ClassNotFoundException e) {
                notifyConnectionStatusChanged(false);
            }
        }
    }

    public void run() {
        /*
        метод run() создавает вспомогательный поток SocketThread, ожидает пока тот установит соединение с сервером,
        а после этого в цикле считывает сообщения с консоли и отправляет их серверу.
        Условием выхода из цикла будет отключение клиента или ввод пользователем команды 'exit'.
         */
        SocketThread socketThread = getSocketThread();
        socketThread.setDaemon(true); // это нужно для того, чтобы при выходе из программы вспомогательный поток прервался автоматически.
        socketThread.start();
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента.");
                return;
            }
        }

        if (clientConnected) {
            ConsoleHelper.writeMessage("Соединение установлено. Для выхода наберите команду 'exit'.");
        } else {
            ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента.");
        }
        // Пока не будет введена команда exit, считываем сообщения с консоли и отправляем их на сервер
        while (clientConnected) {
            String message = ConsoleHelper.readString();
            if (message.equals("exit")) break;
            if (shouldSendTextFromConsole()) {
                sendTextMessage(message);
            }
        }
    }

    // Запрашивает ввод адреса сервера у пользователя и возвращает введенное значение.
    protected String getServerAddress() {
        ConsoleHelper.writeMessage("Введите адрес сервера:");
        return ConsoleHelper.readString();
    }

    // Запрашивает ввод порта сервера и возвращает его.
    protected int getServerPort() {
        ConsoleHelper.writeMessage("Введите порт сервера:");
        return ConsoleHelper.readInt();
    }

    // Запрашивает и возвращает имя пользователя.
    protected String getUserName() {
        ConsoleHelper.writeMessage("Введите ваше имя:");
        return ConsoleHelper.readString();
    }

    protected boolean shouldSendTextFromConsole() {
        /*
        в данной реализации клиента всегда должен возвращать true (мы всегда отправляем текст введенный в консоль).
        Этот метод может быть переопределен, если мы будем писать какой-нибудь другой клиент, унаследованный от нашего,
        который не должен отправлять введенный в консоль текст.
        */
        return true;
    }

    // Создает и возвращает новый объект класса SocketThread.
    protected SocketThread getSocketThread() {
        return new SocketThread();
    }

    // Создает новое текстовое сообщение и отправляет его серверу через соединение connection.
    protected void sendTextMessage(String text) {
        try {
            connection.send(new Message(MessageType.TEXT, text));
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Не удалось отправить сообщение");
            clientConnected = false;
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
}

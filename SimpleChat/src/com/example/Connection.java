package com.example;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Класс соединения между клиентом и сервером
 * Класс Connection выполняет роль обертки над классом java.net.Socket
 */
public class Connection implements Closeable {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        /*
        Создать объект класса ObjectOutputStream нужно до того,
        как будет создаваться объект класса ObjectInputStream,
        иначе может возникнуть взаимная блокировка потоков,
        которые хотят установить соединение через класс Connection.
         */
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    // Запись (сериализация) сообщения
    public void send(Message message) throws IOException {
        synchronized (out) {
            out.writeObject(message);
            out.flush();
        }
    }

    // Чтение (десериализация) сообщения
    public Message receive() throws IOException, ClassNotFoundException {
        synchronized (in) {
            return (Message) in.readObject();
        }
    }
    
    // Возвращает удаленный адрес сокетного соединения
    public SocketAddress getRemoteSocketAddress() {
        return socket.getRemoteSocketAddress();
    }

    @Override
    public void close() throws IOException {
        socket.close();
        out.close();
        in.close();
    }
}

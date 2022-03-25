package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Вспомогательный класс, для чтения или записи в консоль
 */
public class ConsoleHelper {
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    // Вывод сообщения в консоль
    public static void writeMessage(String message) {
        System.out.println(message);
    }

    // Чтение строки с консоли
    public static String readString() {
        while (true) {
            try {
                String buf = bufferedReader.readLine();
                if (buf != null) return buf;
            } catch (IOException e) {
                writeMessage("Произошла ошибка при попытке ввода текста. Попробуйте еще раз.");
            }
        }
    }

    // Парсинк строки в int
    public static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(readString().trim());
            } catch (NumberFormatException e) {
                writeMessage("Произошла ошибка при попытке ввода числа. Попробуйте еще раз.");
            }
        }
    }
}

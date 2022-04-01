package com.company;

import com.company.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    private static final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return consoleReader.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> chosenDishes = new ArrayList<>();

        writeMessage("Please choose a dish from the list: " + Dish.allDishesToString());
        writeMessage("or type 'exit' to complete the order");

        while (true) {
            String dishName = readString().trim();
            if ("exit".equals(dishName)) {
                break;
            }

            try {
                chosenDishes.add(Dish.valueOf(dishName));
                writeMessage(dishName + " has been successfully added to your order");
            } catch (Exception e) {
                writeMessage(dishName + " hasn't been detected");
            }
        }

        return chosenDishes;
    }
}

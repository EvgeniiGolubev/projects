package com.company.kitchen;

import com.company.ConsoleHelper;

import java.util.Observable;
import java.util.Observer;

/**
 * Официант
 */
public class Waiter implements Observer {
    @Override
    public void update(Observable cook, Object order) {
        ConsoleHelper.writeMessage(order + " was cooked by " + cook);
    }
}

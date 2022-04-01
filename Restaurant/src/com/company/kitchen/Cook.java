package com.company.kitchen;

import com.company.ConsoleHelper;
import com.company.statistic.StatisticManager;
import com.company.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Повар
 */
public class Cook extends Observable implements Runnable {
    private final String name;
    private boolean busy;
    private LinkedBlockingQueue<Order> queue;

    public Cook(String name) {
        this.name = name;
    }

    public void startCookingOrder(Order order) {
        this.busy = true;
        ConsoleHelper.writeMessage(name + " start cooking - " + order);
        
        int totalCookingTime = order.getTotalCookingTime();
        CookedOrderEventDataRow row
                = new CookedOrderEventDataRow(order.getTablet().toString(), name, totalCookingTime * 60, order.getDishes());
        StatisticManager.getInstance().register(row); // регистрация заказа
        
        try {
            Thread.sleep(totalCookingTime * 10); // Готовит
        } catch (InterruptedException ignored) {
        }
        
        setChanged();
        notifyObservers(order);
        this.busy = false;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(10);
                if (!queue.isEmpty() && !this.isBusy()) {
                    this.startCookingOrder(queue.take());
                }
            }
        } catch (InterruptedException ignore) {
        }
    }
}

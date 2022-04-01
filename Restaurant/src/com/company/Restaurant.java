package com.company;

import com.company.kitchen.Cook;
import com.company.kitchen.Order;
import com.company.kitchen.Waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Main class
 */
public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;
    private static final LinkedBlockingQueue<Order> ORDER_QUEUE = new LinkedBlockingQueue<>(200);

    public static void main(String[] args) {
        List<Tablet> tablets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Tablet tablet = new Tablet(i + 1);
            tablet.setQueue(ORDER_QUEUE);
            tablets.add(tablet);
        }
        Cook firstCook = new Cook("Biba");
        firstCook.setQueue(ORDER_QUEUE);
        Cook secondCook = new Cook("Boba");
        secondCook.setQueue(ORDER_QUEUE);
        
        Waiter waiter = new Waiter();
        firstCook.addObserver(waiter);
        secondCook.addObserver(waiter);

        Thread firstCookThread = new Thread(firstCook);
        Thread secondCookThread = new Thread(secondCook);
        firstCookThread.setDaemon(true);
        secondCookThread.setDaemon(true);

        Thread orderThread = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
        firstCookThread.start();
        secondCookThread.start();
        orderThread.start();

        try {
            Thread.sleep(1000);
            orderThread.interrupt();
            orderThread.join();
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {
        }

        // Show statistic
        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();
    }
}

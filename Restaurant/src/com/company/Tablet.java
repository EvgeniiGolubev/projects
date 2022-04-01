package com.company;


import com.company.ad.AdvertisementManager;
import com.company.ad.NoVideoAvailableException;
import com.company.kitchen.Order;
import com.company.kitchen.TestOrder;


import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Планшет
 */
public class Tablet {
    private final int number; // номер планшета
    private static final Logger logger = Logger.getLogger(Tablet.class.getName());
    private LinkedBlockingQueue<Order> queue;

    public Tablet(int number) {
        this.number = number;
    }

    // создавать заказ из тех блюд, которые выберет пользователь
    public void createOrder() {
        Order order = null;

        try {
            order = new Order(this);
            processOrder(order);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        } catch (NoVideoAvailableException noVideo) {
            logger.log(Level.INFO, "No video is available for the order " + order);
        }
    }

    public void createTestOrder() {
        Order order = null;

        try {
            order = new TestOrder(this);
            processOrder(order);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        } catch (NoVideoAvailableException noVideo) {
            logger.log(Level.INFO, "No video is available for the order " + order);
        }
    }

    private boolean processOrder(Order order) {
        if (order.isEmpty()) {
            return true;
        }
        
        queue.offer(order);

        ConsoleHelper.writeMessage(order.toString());
        AdvertisementManager advertisementManager = new AdvertisementManager(order.getTotalCookingTime() * 60); // реклама
        advertisementManager.processVideos();

        return false;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "number=" + number +
                '}';
    }
}

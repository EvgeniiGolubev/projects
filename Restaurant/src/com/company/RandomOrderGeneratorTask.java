package com.company;

import java.util.List;

public class RandomOrderGeneratorTask implements Runnable {
    private List<Tablet> tablets;
    private int interval;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval) {
        this.tablets = tablets;
        this.interval = interval;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int k = (int) (Math.random() * tablets.size());
                Tablet expected = tablets.get(k);
//                Для того, что бы запустить рандомный набор заказов, раскоментируй тут
//                expected.createTestOrder();
                expected.createOrder();
                Thread.sleep(interval);
            }
        } catch (InterruptedException e) {
        }
    }
}

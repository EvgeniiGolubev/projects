package com.company.ad;

import com.company.ConsoleHelper;
import com.company.statistic.StatisticManager;
import com.company.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.List;

/**
 * У каждого планшета будет свой объект менеджера,
 * который будет подбирать оптимальный набор роликов и их последовательность для каждого заказа.
 * Он также будет взаимодействовать с плеером и отображать ролики.
 */
public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds;
    
    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() {
        if (storage.list().isEmpty()) {
            throw new NoVideoAvailableException();
        }

        List<Advertisement> advertisements = sortVideos(storage.list());

        //регистрация выбраных роликов
        long maxAmount = advertisements.stream().mapToLong(Advertisement::getAmountPerOneDisplaying).sum();
        int totalDuration = advertisements.stream().mapToInt(Advertisement::getDuration).sum();
        VideoSelectedEventDataRow row = new VideoSelectedEventDataRow(advertisements, maxAmount, totalDuration);
        StatisticManager.getInstance().register(row);


        // показ и подтверждение выбраных роликов
        advertisements.forEach(this::displayInPlayer);
        advertisements.forEach(Advertisement::revalidate);
    }


    private List<Advertisement> sortVideos(List<Advertisement> advertisements) {
        // В этом методе нужно подобрать список видео из доступных, просмотр которых обеспечивает максимальную выгоду
        List<Advertisement> result = new ArrayList<>();
        advertisements.sort((o1, o2) -> (int) (o2.getAmountPerOneDisplaying() - o1.getAmountPerOneDisplaying()));

        int time = timeSeconds;
        for (Advertisement a : advertisements) {
            if (a.getDuration() <= time && a.isActive()) {
                result.add(a);
                time -= a.getDuration();
            }
        }

        return result;
    }

    //Вывод роликов в консоль
    private void displayInPlayer(Advertisement advertisement) {
        ConsoleHelper.writeMessage(advertisement.getName() + " is displaying... " + advertisement.getAmountPerOneDisplaying() +
                ", " + (1000 * advertisement.getAmountPerOneDisplaying() / advertisement.getDuration()));
    }


//----------------------------------------------------------------------------------------------------
//Нужно подобрать список видео из доступных, просмотр которых обеспечивает максимальную выгоду
//Этот набор должен удовлетворять следующим требованиям:
//1) сумма денег, полученная от показов, должна быть максимальной из всех возможных вариантов
//2) общее время показа рекламных роликов НЕ должно превышать время приготовления блюд для текущего заказа;
//3) для одного заказа любой видео-ролик показывается не более одного раза;
//4) если существует несколько вариантов набора видео-роликов с одинаковой суммой денег, полученной от показов, то:
//  4.1) выбрать тот вариант, у которого суммарное время максимальное;
//  4.2) если суммарное время у этих вариантов одинаковое, то выбрать вариант с минимальным количеством роликов;
//5) количество показов у любого рекламного ролика из набора - положительное число.

// Решение с сайта

//    public void processVideos() {
//        this.totalTimeSecondsLeft = Integer.MAX_VALUE;
//        obtainOptimalVideoSet(new ArrayList<>(), timeSeconds, 0L);
//
//        displayAdvertisement();
//    }
//
//    //recursion
//    private long maxAmount;
//    private List<Advertisement> optimalVideoSet;
//    private int totalTimeSecondsLeft;
//
//    private void obtainOptimalVideoSet(List<Advertisement> totalList, int currentTimeSecondsLeft, long currentAmount) {
//        if (currentTimeSecondsLeft < 0) {
//            return;
//        } else if (currentAmount > maxAmount
//                || currentAmount == maxAmount && (totalTimeSecondsLeft > currentTimeSecondsLeft
//                || totalTimeSecondsLeft == currentTimeSecondsLeft && totalList.size() < optimalVideoSet.size())) {
//            this.totalTimeSecondsLeft = currentTimeSecondsLeft;
//            this.optimalVideoSet = totalList;
//            this.maxAmount = currentAmount;
//            if (currentTimeSecondsLeft == 0) {
//                return;
//            }
//        }
//
//        ArrayList<Advertisement> tmp = getActualAdvertisements();
//        tmp.removeAll(totalList);
//        for (Advertisement ad : tmp) {
//            if (!ad.isActive()) continue;
//            ArrayList<Advertisement> currentList = new ArrayList<>(totalList);
//            currentList.add(ad);
//            obtainOptimalVideoSet(currentList, currentTimeSecondsLeft - ad.getDuration(), currentAmount + ad.getAmountPerOneDisplaying());
//        }
//    }
//
//    private ArrayList<Advertisement> getActualAdvertisements() {
//        ArrayList<Advertisement> advertisements = new ArrayList<>();
//        for (Advertisement ad : storage.list()) {
//            if (ad.isActive()) {
//                advertisements.add(ad);
//            }
//        }
//        return advertisements;
//    }
//
//    private void displayAdvertisement() {
//        if (optimalVideoSet == null || optimalVideoSet.isEmpty()) {
//            throw new NoVideoAvailableException();
//        }
//
//        optimalVideoSet.sort((o1, o2) -> {
//            long l = o2.getAmountPerOneDisplaying() - o1.getAmountPerOneDisplaying();
//            return (int) (l != 0 ? l : o2.getDuration() - o1.getDuration());
//        });
//
//        for (Advertisement ad : optimalVideoSet) {
//            displayInPlayer(ad);
//            ad.revalidate();
//        }
//    }
//
//    private void displayInPlayer(Advertisement advertisement) {
//        System.out.println(advertisement.getName() + " is displaying... " + advertisement.getAmountPerOneDisplaying() +
//                ", " + (1000 * advertisement.getAmountPerOneDisplaying() / advertisement.getDuration()));
//    }
//----------------------------------------------------------------------------------------------------
}

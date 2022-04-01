package com.company;

import com.company.ad.Advertisement;
import com.company.ad.StatisticAdvertisementManager;
import com.company.statistic.StatisticManager;

import java.util.*;

public class DirectorTablet {

    public void printAdvertisementProfit() {
        StatisticManager statisticManager = StatisticManager.getInstance();
        Map<String, Long> profitMap = statisticManager.getProfitMap();
        ArrayList<String> list = new ArrayList(profitMap.keySet());
        Collections.sort(list);

        for (String key : list) {
            double amount = 1.0 * profitMap.get(key) / 100;
            System.out.println(key + " - " + String.format(Locale.ENGLISH, "%.2f", amount));
        }
    }

    public void printCookWorkloading() {
        StatisticManager statisticManager = StatisticManager.getInstance();
        Map<String, Map<String, Integer>> cookWorkloadingMap = statisticManager.getCookWorkloadingMap();
        ArrayList<String> list = new ArrayList(cookWorkloadingMap.keySet());
        Collections.sort(list);

        for (String key : list) {
            Map<String, Integer> cookMap = cookWorkloadingMap.get(key);
            System.out.println(key);

            ArrayList<String> cookNames = new ArrayList(cookMap.keySet());
            Collections.sort(cookNames);
            for (String cookName : cookNames) {
                System.out.println(cookName + " - " + ((cookMap.get(cookName) + 59) / 60) + " min");
            }

            System.out.println();
        }
    }

    public void printActiveVideoSet() {
        List<Advertisement> list = StatisticAdvertisementManager.getInstance().getAllVideos(true);
        for (Advertisement ad : list) {
            ConsoleHelper.writeMessage(ad.getName() + " - " + ad.getHits());
        }
    }

    public void printArchivedVideoSet() {
        List<Advertisement> list = StatisticAdvertisementManager.getInstance().getAllVideos(false);
        for (Advertisement ad : list) {
            ConsoleHelper.writeMessage(ad.getName());
        }
    }
}

//        Мое решение валидатор не принимает, оно вроде правильное, но бороться с валидатором я не стану
//        public void printCookWorkloading() {
//            Map<Date, Map<String, Integer>> map = StatisticManager.getInstance().cooksWorkingHours();
//            List<String> list = new ArrayList<>();
//            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
//            for (Map.Entry<Date, Map<String, Integer>> pair : map.entrySet()) {
//                ConsoleHelper.writeMessage(formatter.format(pair.getKey()));
//                Map<String, Integer> innerMap = pair.getValue();
//                for (Map.Entry<String, Integer> innerPair : innerMap.entrySet()) {
//                    list.add(innerPair.getKey() + " - " + innerPair.getValue() / 60 + " min");
//                }
//                Collections.reverse(list);
//                list.forEach(System.out::println);
//            }
//        }

//        Мое решение валидатор не принимает, оно вроде правильное, но бороться с валидатором я не стану
//        public void printAdvertisementProfit() {
//            Map<Date, Long> map = StatisticManager.getInstance().calculateAdRevenue();
//            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
//            double total = 0;
//            for (Map.Entry<Date, Long> pair : map.entrySet()) {
//                double value = 1.0 * pair.getValue() / 100;
//                total += value;
//                ConsoleHelper.writeMessage(formatter.format(pair.getKey()) + " - " + String.format(Locale.ENGLISH, "%.2f", value));
//            }
//            ConsoleHelper.writeMessage("Total - " + String.format(Locale.ENGLISH, "%.2f", total));
//        }

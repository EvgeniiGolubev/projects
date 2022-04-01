package com.company.statistic;

import com.company.statistic.event.CookedOrderEventDataRow;
import com.company.statistic.event.EventDataRow;
import com.company.statistic.event.EventType;
import com.company.statistic.event.VideoSelectedEventDataRow;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * С помощью StatisticManager будем регистрировать события в хранилище.
 */
public class StatisticManager {
    private StatisticStorage statisticStorage = new StatisticStorage();

    private StatisticManager() {}

    private static class InstanceHolder {
        private static final StatisticManager ourInstance = new StatisticManager();
    }

    public static StatisticManager getInstance() {
        return InstanceHolder.ourInstance;
    }

    // Метод register должен регистрировать события в хранилище.
    public void register(EventDataRow data) {
        this.statisticStorage.put(data);
    }

    // хранилище
    private class StatisticStorage {
        private Map<EventType, List<EventDataRow>> storage = new HashMap<>();

        private StatisticStorage() {
            for (EventType e : EventType.values()) {
                this.storage.put(e, new ArrayList<EventDataRow>());
            }
        }

        private void put(EventDataRow data) {
            EventType type = data.getType();
            if (!this.storage.containsKey(type))
                throw new UnsupportedOperationException();

            this.storage.get(type).add(data);
        }

        private List<EventDataRow> get(EventType type) {
            if (!this.storage.containsKey(type))
                throw new UnsupportedOperationException();

            return this.storage.get(type);
        }
    }

//  Метод посчитает общую прибыль за каждый день.
    public Map<String, Long> getProfitMap() {
        Map<String, Long> res = new HashMap();
        List<EventDataRow> rows = statisticStorage.get(EventType.SELECTED_VIDEOS);
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        long total = 0l;
        for (EventDataRow row : rows) {
            VideoSelectedEventDataRow dataRow = (VideoSelectedEventDataRow) row;
            String date = format.format(dataRow.getDate());
            if (!res.containsKey(date)) {
                res.put(date, 0l);
            }
            total += dataRow.getAmount();
            res.put(date, res.get(date) + dataRow.getAmount());
        }

        res.put("Total", total);

        return res;
    }

//  Метод считает общую продолжительность работы для каждого повара отдельно.
    public Map<String, Map<String, Integer>> getCookWorkloadingMap() {
        Map<String, Map<String, Integer>> res = new HashMap(); //name, time
        List<EventDataRow> rows = statisticStorage.get(EventType.COOKED_ORDER);
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        for (EventDataRow row : rows) {
            CookedOrderEventDataRow dataRow = (CookedOrderEventDataRow) row;
            String date = format.format(dataRow.getDate());
            if (!res.containsKey(date)) {
                res.put(date, new HashMap<String, Integer>());
            }
            Map<String, Integer> cookMap = res.get(date);
            String cookName = dataRow.getCookName();
            if (!cookMap.containsKey(cookName)) {
                cookMap.put(cookName, 0);
            }

            Integer totalTime = cookMap.get(cookName);
            cookMap.put(cookName, totalTime + dataRow.getTime());
        }

        return res;
    }
}


//    Метод посчитает общую прибыль за каждый день.
//    Мое решение валидатор не принимает, оно вроде правильное, но бороться с валидатором я не стану
//    public Map<Date, Long> calculateAdRevenue() {
//        Map<Date, Long> result = new TreeMap<>(Collections.reverseOrder());
//        List<EventDataRow> videos = statisticStorage.get(EventType.SELECTED_VIDEOS);
//
//        for (EventDataRow e : videos) {
//            VideoSelectedEventDataRow video = (VideoSelectedEventDataRow) e;
//            Date key = video.getDate();
//            long value = video.getAmount();
//
//            if (result.containsKey(key)) {
//                long oldValue = result.get(key);
//                result.put(key, value + oldValue);
//            } else {
//                result.put(key, value);
//            }
//        }
//        return result;
//    }

//    Метод считает общую продолжительность работы для каждого повара отдельно.
//    Мое решение валидатор не принимает, оно вроде правильное, но бороться с валидатором я не стану
//    public Map<Date, Map<String, Integer>> cooksWorkingHours() {
//        Map<Date, Map<String, Integer>> result = new TreeMap<>(Collections.reverseOrder());
//
//        List<EventDataRow> cooks = statisticStorage.get(EventType.COOKED_ORDER);
//
//        for (EventDataRow e : cooks) {
//            CookedOrderEventDataRow cooker = (CookedOrderEventDataRow) e;
//
//            Date key = cooker.getDate();
//            String cookName = cooker.getCookName();
//            int cookTime = cooker.getTime();
//
//            if (!result.containsKey(key)) {
//                Map<String, Integer> value = new HashMap<>();
//                value.put(cookName, cookTime);
//                result.put(key, value);
//            } else {
//                Map<String, Integer> innerValue = result.get(key);
//
//                if (innerValue.containsKey(cookName)) {
//                    int oldCookTime = innerValue.get(cookName);
//                    innerValue.put(cookName, cookTime + oldCookTime);
//                } else {
//                    innerValue.put(cookName, cookTime);
//                }
//
//                result.put(key, innerValue);
//            }
//        }
//
//        return result;
//    }
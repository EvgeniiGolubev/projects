package com.company.ad;

import java.util.ArrayList;
import java.util.List;

public class StatisticAdvertisementManager {
    private AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager() {}

    private static class InstanceHolder {
        private static final StatisticAdvertisementManager ourInstance = new StatisticAdvertisementManager();
    }

    public static StatisticAdvertisementManager getInstance() {
        return InstanceHolder.ourInstance;
    }

    public List<Advertisement> getAllVideos(boolean isActive) {
        List<Advertisement> result = new ArrayList<>();

        for (Advertisement ad : advertisementStorage.list()) {
            if (isActive) {
                if (ad.isActive()) {
                    result.add(ad);
                }
            } else {
                if (!ad.isActive()) {
                    result.add(ad);
                }
            }
        }

        result.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));

        return result;
    }
}

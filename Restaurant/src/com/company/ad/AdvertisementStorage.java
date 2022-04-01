package com.company.ad;

import java.util.ArrayList;
import java.util.List;

/**
 * Хранилище рекламных роликов
 */
public class AdvertisementStorage {
    private final List<Advertisement> videos = new ArrayList<>();
    private AdvertisementStorage() {
        //init random videos
        Object someContent = new Object();
        videos.add(new Advertisement(someContent, "First Video", 5000, 100, 3 * 60));
        videos.add(new Advertisement(someContent, "Second Video", 100, 10, 15 * 60));
        videos.add(new Advertisement(someContent, "Third Video", 400, 2, 10 * 60));
    }
    
    private static class InstanceHolder {
        private static final AdvertisementStorage ourInstance = new AdvertisementStorage();
    }

    public static AdvertisementStorage getInstance() {
        return InstanceHolder.ourInstance;
    }

    public List<Advertisement> list() {
        return this.videos;
    }

    public void add(Advertisement advertisement) {
        videos.add(advertisement);
    }
}

package com.jumbo.assessment.domain.service;

import com.jumbo.assessment.domain.entity.Store;
import com.jumbo.assessment.domain.repository.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StoreService {
    private static final Logger log = LoggerFactory.getLogger(StoreService.class);
    private static final double EARTH_RADIUS_KM = 6371.0;

    private final StoreRepository repository;

    public StoreService(StoreRepository repository) {
        this.repository = repository;
    }

    public List<Store> findClosest(double latitude, double longitude) {
        log.debug("Searching closest store for location: latitude={}, longitude={}",
                latitude, longitude);

        List<Store> stores = this.repository.loadStore();

        return stores.stream()
                .map(store -> {
                    try {
                        double storeLatitude = Double.parseDouble(store.latitude());
                        double storeLongitude = Double.parseDouble(store.longitude());
                        double distance = calculateDistance(latitude, longitude, storeLatitude, storeLongitude);

                        return new AbstractMap.SimpleEntry<>(store, distance);
                    } catch (NumberFormatException e) {
                        log.warn("Error to convert store latitude and latitude {}: lat={}, lon={}",
                                store.uuid(), store.latitude(), store.longitude(), e);

                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingDouble(Map.Entry::getValue))
                .limit(5)
                .map(Map.Entry::getKey)
                .toList();
    }

    /**
     * Method used for calculate the distance between the client location and a store location
     * using latitude and longitude coordinates based on earth radius.
     *
     * @param clientLatitude  the latitude of the client location
     * @param clientLongitude the longitude of the client location
     * @param storeLatitude   the latitude of the store location
     * @param storeLongitude  the longitude of the store location
     * @return the distance in kilometers between the client and the store
    */
    private double calculateDistance(double clientLatitude, double clientLongitude, double storeLatitude, double storeLongitude) {
        double latitudeDistance = Math.toRadians(storeLatitude - clientLatitude);
        double longitudeDistance = Math.toRadians(storeLongitude - clientLongitude);

        clientLatitude = Math.toRadians(clientLatitude);
        storeLatitude = Math.toRadians(storeLatitude);

        double a = Math.pow(Math.sin(latitudeDistance / 2), 2)
                + Math.cos(clientLatitude) * Math.cos(storeLatitude)
                * Math.pow(Math.sin(longitudeDistance / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }
}

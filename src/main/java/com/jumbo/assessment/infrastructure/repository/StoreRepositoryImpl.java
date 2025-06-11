package com.jumbo.assessment.infrastructure.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumbo.assessment.domain.entity.Store;
import com.jumbo.assessment.domain.repository.StoreRepository;
import com.jumbo.assessment.infrastructure.exception.LoadStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.List;

@Component
public class StoreRepositoryImpl implements StoreRepository {
    private static final Logger log = LoggerFactory.getLogger(StoreRepositoryImpl.class);

    private final ObjectMapper mapper;

    private SoftReference<List<Store>> stores;

    public StoreRepositoryImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Store> loadStore() {
        if(stores != null) {
            return stores.get();
        }

        try (final InputStream is = new ClassPathResource("data/stores.json").getInputStream()) {
            JsonNode root = mapper.readTree(is);
            JsonNode storesNode = root.get("stores");
            List<Store> storesMapped = List.copyOf(mapper.convertValue(storesNode, new TypeReference<>() {}));

            this.stores = new SoftReference<>(storesMapped);

            return this.stores.get();
        } catch (Exception e) {
            log.error("Error to load stores path: data/stores.json");

            throw new LoadStoreException("Error to load stores.");
        }
    }
}

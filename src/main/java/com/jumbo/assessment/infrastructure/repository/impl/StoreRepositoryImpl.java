package com.jumbo.assessment.infrastructure.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumbo.assessment.domain.entity.Store;
import com.jumbo.assessment.infrastructure.exception.LoadStoreException;
import com.jumbo.assessment.infrastructure.repository.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class StoreRepositoryImpl implements StoreRepository {
    private static final Logger log = LoggerFactory.getLogger(StoreRepositoryImpl.class);

    private final ObjectMapper mapper;

    public StoreRepositoryImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Store> loadStore() {
        try (final InputStream is = new ClassPathResource("data/stores.json").getInputStream()) {
            JsonNode root = mapper.readTree(is);
            JsonNode storesNode = root.get("stores");

            return List.copyOf(mapper.convertValue(storesNode, new TypeReference<>() {}));
        } catch (Exception e) {
            log.warn("Error to load stores JSON");

            throw new LoadStoreException("Error to load stores JSON");
        }
    }
}

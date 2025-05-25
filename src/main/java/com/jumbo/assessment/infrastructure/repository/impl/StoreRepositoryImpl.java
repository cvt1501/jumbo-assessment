package com.jumbo.assessment.infrastructure.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumbo.assessment.domain.dto.StoreWrapper;
import com.jumbo.assessment.infrastructure.exception.StoreLoadException;
import com.jumbo.assessment.infrastructure.repository.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class StoreRepositoryImpl implements StoreRepository {
    private static final Logger log = LoggerFactory.getLogger(StoreRepositoryImpl.class);

    private final ObjectMapper mapper;

    public StoreRepositoryImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public StoreWrapper loadJson() {
        try (final InputStream is = new ClassPathResource("data/stores.json").getInputStream()) {
            return mapper.readValue(is, new TypeReference<>() {});
        } catch (Exception e) {
            log.warn("Error to load stores JSON");

            throw new StoreLoadException("Error to load stores JSON");
        }
    }
}

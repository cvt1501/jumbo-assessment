package com.jumbo.assessment.infrastructure.repository;

import com.jumbo.assessment.domain.dto.StoreWrapper;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository {
    StoreWrapper loadJson();
}

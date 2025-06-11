package com.jumbo.assessment.domain.repository;

import com.jumbo.assessment.domain.entity.Store;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository {
    List<Store> loadStore();
}

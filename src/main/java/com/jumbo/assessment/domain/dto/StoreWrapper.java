package com.jumbo.assessment.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jumbo.assessment.domain.entity.Store;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@RegisterReflectionForBinding
public record StoreWrapper(List<Store> stores) {
    public StoreWrapper {
        stores = List.copyOf(stores);
    }
}

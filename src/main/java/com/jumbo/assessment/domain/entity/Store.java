package com.jumbo.assessment.domain.entity;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;

@RegisterReflectionForBinding
public record Store(
        String city,
        String postalCode,
        String street,
        String street2,
        String street3,
        String addressName,
        String uuid,
        String longitude,
        String latitude,
        String complexNumber,
        boolean showWarningMessage,
        String todayOpen,
        String locationType,
        boolean collectionPoint,
        String sapStoreID,
        String todayClose
) {}

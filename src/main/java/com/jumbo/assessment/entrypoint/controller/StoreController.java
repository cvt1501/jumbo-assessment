package com.jumbo.assessment.entrypoint.controller;

import com.jumbo.assessment.domain.entity.Store;
import com.jumbo.assessment.domain.service.StoreService;
import com.jumbo.assessment.entrypoint.dto.DefaultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {
    private static final Logger log = LoggerFactory.getLogger(StoreController.class);

    private final StoreService service;

    public StoreController(StoreService service) {
        this.service = service;
    }

    @GetMapping("/closest")
    public ResponseEntity<DefaultResponse<List<Store>>> findClosest(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude
    ) {
        List<Store> closestStore = service.findClosest(latitude, longitude);

        log.trace("Closest stores: {}", closestStore.stream().map(Store::uuid).toList());

        return ResponseEntity.ok(
                new DefaultResponse<>(HttpStatus.OK.getReasonPhrase(), closestStore));
    }
}

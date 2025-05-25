package com.jumbo.assessment.unit;

import com.jumbo.assessment.domain.dto.StoreWrapper;
import com.jumbo.assessment.domain.entity.Store;
import com.jumbo.assessment.domain.service.StoreService;
import com.jumbo.assessment.infrastructure.repository.StoreRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {
    @Mock
    private StoreRepository repository;

    @InjectMocks
    private StoreService service;

    @Test
    @DisplayName("Should return the 5 closest stores ordered by distance")
    void shouldReturnFiveClosestStoresInOrder() {
        List<Store> stores = List.of(
                buildStore("1", "51.456158", "5.804116"),
                buildStore("2", "51.456159", "5.804117"),
                buildStore("3", "51.456167", "5.804118"),
                buildStore("4", "21.456127", "2.804117"), // far
                buildStore("5", "51.456177", "5.804119"),
                buildStore("6", "21.456127", "2.804116"), // far
                buildStore("7", "51.456127", "5.804116")
        );
        StoreWrapper mockWrapper = new StoreWrapper(stores);

        when(repository.loadJson()).thenReturn(mockWrapper);

        StoreWrapper response = service.findClosest(51.456157, 5.804116);
        List<String> resultUuids = response.stores().stream()
                .map(Store::uuid)
                .toList();
        List<String> expectedUuids = List.of("1", "2", "3", "5", "7");

        assertEquals(expectedUuids, resultUuids,
                "Expected the 5 closest stores ordered by proximity to (51.456157, 5.804116)");
        verify(repository, times(1)).loadJson();
    }

    @Test
    @DisplayName("Should filter out stores which contains latitude or longitude in wrong format (not double)")
    void shouldFilterStoresWithWrongNumberFormatOnLatAndLon() {
        List<Store> stores = List.of(
                buildStore("1", "51.456158", "5.804116"),
                buildStore("2", "WRONG FORMAT", "WRONG FORMAT"), // wrong format
                buildStore("3", "51.456167", "5.804118"),
                buildStore("4", "WRONG FORMAT", "WRONG FORMAT"), // wrong format
                buildStore("5", "51.456177", "5.804119"),
                buildStore("6", "51.456127", "5.804116")
        );
        StoreWrapper mockWrapper = new StoreWrapper(stores);

        when(repository.loadJson()).thenReturn(mockWrapper);

        StoreWrapper response = service.findClosest(51.456157, 5.804116);

        assertEquals(4, response.stores().size());
        verify(repository, times(1)).loadJson();
    }

    private Store buildStore(String uuid, String latitude, String longitude) {
        return Instancio.of(Store.class)
                .supply(field("uuid"), () -> uuid)
                .supply(field("latitude"), () -> latitude)
                .supply(field("longitude"), () -> longitude)
                .create();
    }
}

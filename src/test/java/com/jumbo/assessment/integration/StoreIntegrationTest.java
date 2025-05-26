package com.jumbo.assessment.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StoreIntegrationTest {

    @LocalServerPort
    private int port;

    @Test
    @DisplayName("Should return 5 closest stores for latitude 51.456157 and longitude 5.804116")
    void shouldFindClosestStores() throws IOException, URISyntaxException {
        WebTestClient client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        String expectedJson = Files.readString(
                Path.of(ClassLoader.getSystemResource("response/find-closest-response.json").toURI())
        );

        client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stores/closest")
                        .queryParam("latitude", 51.456157)
                        .queryParam("longitude", 5.804116)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json(expectedJson);
    }
}

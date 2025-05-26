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
    private static final String ROUTE = "/api/stores/closest";

    @LocalServerPort
    private int port;

    @Test
    @DisplayName("Should return 5 closest stores for latitude 51.456157 and longitude 5.804116")
    void shouldFindClosestStores() throws IOException, URISyntaxException {
        WebTestClient client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        String expectedJson = Files.readString(
                Path.of(ClassLoader.getSystemResource("response/closest-response.json").toURI())
        );

        client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(ROUTE)
                        .queryParam("latitude", 51.456157)
                        .queryParam("longitude", 5.804116)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json(expectedJson);
    }

    @Test
    @DisplayName("Should throw bad request when client sent wrong format query parameters")
    void shouldThrowBadRequestWhenQueryParametersInWrongFormat() throws IOException, URISyntaxException {
        WebTestClient client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        String expectedJson = Files.readString(
                Path.of(ClassLoader.getSystemResource("response/closest-bad-request-wrong-parameters-response.json").toURI())
        );

        client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(ROUTE)
                        .queryParam("latitude", 51.456157)
                        .queryParam("longitude", "Wrong format")
                        .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .json(expectedJson);
    }

    @Test
    @DisplayName("Should throw bad request when client doesn't send the query parameters")
    void shouldThrowBadRequestWhenNoQueryParameters() throws IOException, URISyntaxException {
        WebTestClient client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        String expectedJson = Files.readString(
                Path.of(ClassLoader.getSystemResource("response/closest-bad-request-no-parameters-response.json").toURI())
        );

        client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(ROUTE)
                        .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .json(expectedJson);
    }
}

package co.accenture.pruebatecnica.infrastructure.web;

import co.accenture.pruebatecnica.application.dto.FranchiseRequest;
import co.accenture.pruebatecnica.application.dto.Response;
import co.accenture.pruebatecnica.application.service.FranchiseService;
import co.accenture.pruebatecnica.domain.model.Franchise;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@WebFluxTest(controllers = FranchiseController.class)
@Import(FranchiseControllerTest.TestConfig.class)
class FranchiseControllerTest {

    @Autowired
    WebTestClient client;

    @Autowired
    FranchiseService service;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public FranchiseService franchiseService() {
            return Mockito.mock(FranchiseService.class);
        }
    }

    @Test
    @DisplayName("POST /v1/rest/franchises => 201 CREATED")
    void createFranchise() {
        UUID id = UUID.randomUUID();
        FranchiseRequest req = new FranchiseRequest("MyFranchise");
        Franchise saved = new Franchise(id, "MyFranchise");

        Mockito.when(service.create(req.getName()))
                .thenReturn(Mono.just(saved));

        client.post()
                .uri("/v1/rest/franchises")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("Location")
                .expectBody(new ParameterizedTypeReference<Response<Franchise>>() {})
                .value(resp -> {
                    assert resp.getStatus() == HttpStatus.CREATED;
                    assert resp.getData().size() == 1;
                    assert resp.getData().get(0).getId().equals(id);
                });
    }

    @Test
    @DisplayName("PUT /v1/rest/franchises/name/{id} => 200 OK")
    void updateFranchiseName() {
        UUID id = UUID.randomUUID();
        FranchiseRequest req = new FranchiseRequest("NewName");
        Franchise updated = new Franchise(id, "NewName");

        Mockito.when(service.updateName(id, req.getName()))
                .thenReturn(Mono.just(updated));

        client.put()
                .uri("/v1/rest/franchises/name/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<Franchise>>() {})
                .value(resp -> {
                    assert resp.getStatus() == HttpStatus.OK;
                    assert "NewName".equals(resp.getData().get(0).getName());
                });
    }
}
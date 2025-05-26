package co.accenture.pruebatecnica.infrastructure.web;

import co.accenture.pruebatecnica.application.dto.BranchRequest;
import co.accenture.pruebatecnica.application.dto.Response;
import co.accenture.pruebatecnica.application.service.BranchService;
import co.accenture.pruebatecnica.domain.model.Branch;
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

@WebFluxTest(controllers = BranchController.class)
@Import(BranchControllerTest.TestConfig.class)
class BranchControllerTest {

    @Autowired
    WebTestClient client;

    @Autowired
    BranchService service;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public BranchService branchService() {
            return Mockito.mock(BranchService.class);
        }
    }

    @Test
    @DisplayName("POST /v1/rest/branches => 201 CREATED")
    void createBranch() {
        UUID bid = UUID.randomUUID();
        UUID fid = UUID.randomUUID();
        BranchRequest req = new BranchRequest("B123", fid);
        Branch saved = new Branch(bid, "B123", new Franchise(fid, null));

        Mockito.when(service.create(req.getName(), req.getFranchiseId()))
                .thenReturn(Mono.just(saved));

        client.post()
                .uri("/v1/rest/branches")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("Location")
                .expectBody(new ParameterizedTypeReference<Response<Branch>>() {})
                .value(resp -> {
                    assert resp.getStatus() == HttpStatus.CREATED;
                    assert resp.getData().size() == 1;
                    assert resp.getData().get(0).getId().equals(bid);
                });
    }

    @Test
    @DisplayName("PUT /v1/rest/branches/name/{id} => 200 OK")
    void updateBranchName() {
        UUID bid = UUID.randomUUID();
        BranchRequest req = new BranchRequest("B-New", null);
        Branch updated = new Branch(bid, "B-New", null);

        Mockito.when(service.updateName(bid, req.getName()))
                .thenReturn(Mono.just(updated));

        client.put()
                .uri("/v1/rest/branches/name/{id}", bid)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<Branch>>() {})
                .value(resp -> {
                    assert resp.getStatus() == HttpStatus.OK;
                    assert "B-New".equals(resp.getData().get(0).getName());
                });
    }
}

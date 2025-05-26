package co.accenture.pruebatecnica.infrastructure.web;

import co.accenture.pruebatecnica.application.dto.ProductRequest;
import co.accenture.pruebatecnica.application.dto.Response;
import co.accenture.pruebatecnica.application.service.ProductService;
import co.accenture.pruebatecnica.domain.model.Branch;
import co.accenture.pruebatecnica.domain.model.Product;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@WebFluxTest(controllers = ProductController.class)
@Import(ProductControllerTest.TestConfig.class)
class ProductControllerTest {

    @Autowired
    WebTestClient client;

    @Autowired
    ProductService service;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ProductService productService() {
            return Mockito.mock(ProductService.class);
        }
    }

    @Test
    @DisplayName("POST /v1/rest/products => 201 CREATED")
    void createProduct() {
        UUID pid = UUID.randomUUID(), bid = UUID.randomUUID();
        ProductRequest req = new ProductRequest("P123", bid, 5);
        Product saved = new Product(pid, "P123", 5, new Branch(bid, null, null));

        Mockito.when(service.create(req.getName(), req.getAmount(), req.getBranchId()))
                .thenReturn(Mono.just(saved));

        client.post()
                .uri("/v1/rest/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("Location")
                .expectBody(new ParameterizedTypeReference<Response<Product>>() {})
                .value(resp -> {
                    assert resp.getStatus() == HttpStatus.CREATED;
                    assert resp.getData().size() == 1;
                    assert resp.getData().get(0).getAmount() == 5;
                });
    }

    @Test
    @DisplayName("PUT /v1/rest/products/name/{id} => 200 OK")
    void updateProductName() {
        UUID pid = UUID.randomUUID();
        ProductRequest req = new ProductRequest("NewP", null, null);
        Product updated = new Product(pid, "NewP", 5, null);

        Mockito.when(service.updateName(pid, req.getName()))
                .thenReturn(Mono.just(updated));

        client.put()
                .uri("/v1/rest/products/name/{id}", pid)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<Product>>() {})
                .value(resp -> {
                    assert "NewP".equals(resp.getData().get(0).getName());
                });
    }

    @Test
    @DisplayName("GET /v1/rest/products/top-stock/franchise/{id} => 200 OK")
    void topStockByFranchise() {
        UUID fid = UUID.randomUUID(), bid = UUID.randomUUID();
        Product p1 = new Product(UUID.randomUUID(), "P1", 9, new Branch(bid, null, null));
        Product p2 = new Product(UUID.randomUUID(), "P2", 7, new Branch(bid, null, null));

        Mockito.when(service.findTopByFranchise(fid))
                .thenReturn(Flux.just(p1, p2));

        client.get()
                .uri("/v1/rest/products/top-stock/franchise/{id}", fid)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .hasSize(2)
                .contains(p1, p2);
    }

    @Test
    @DisplayName("PUT /v1/rest/products/amount/{id} => 200 OK")
    void updateProductAmount() {
        UUID pid = UUID.randomUUID();
        ProductRequest req = new ProductRequest(null, null, 20);
        Product updated = new Product(pid, "P1", 20, null);

        Mockito.when(service.updateAmount(pid, req.getAmount()))
                .thenReturn(Mono.just(updated));

        client.put()
                .uri("/v1/rest/products/amount/{id}", pid)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<Product>>() {})
                .value(resp -> {
                    assert resp.getData().get(0).getAmount() == 20;
                });
    }
}

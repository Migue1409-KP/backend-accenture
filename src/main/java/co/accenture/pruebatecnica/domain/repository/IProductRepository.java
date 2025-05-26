package co.accenture.pruebatecnica.domain.repository;

import co.accenture.pruebatecnica.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IProductRepository {
    Mono<Product> findById(UUID id);
    Flux<Product> findAll();
    Flux<Product> findAllByBranchId(UUID branchId);
    Mono<Product> save(Product product);
    Flux<Product> findTopProductByFranchise(UUID franchiseId);
    Mono<Void> deleteById(UUID id);
}

package co.accenture.pruebatecnica.domain.repository;

import co.accenture.pruebatecnica.domain.model.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IFranchiseRepository {
    Mono<Franchise> findById(UUID id);
    Flux<Franchise> findAll();
    Mono<Franchise> save(Franchise franchise);
    Mono<Void> deleteById(UUID id);
}

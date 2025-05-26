package co.accenture.pruebatecnica.domain.repository;

import co.accenture.pruebatecnica.domain.model.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IBranchRepository {
    Mono<Branch> findById(UUID id);
    Flux<Branch> findAll();
    Flux<Branch> findAllByFranchiseId(UUID franchiseId);
    Mono<Branch> save(Branch branch);
    Mono<Void> deleteById(UUID id);
}

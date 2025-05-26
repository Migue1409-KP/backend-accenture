package co.accenture.pruebatecnica.application.service;

import co.accenture.pruebatecnica.domain.model.Franchise;
import co.accenture.pruebatecnica.domain.usecase.franchise.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class FranchiseService {
    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final GetFranchiseByIdUseCase getFranchiseByIdUseCase;
    private final ListFranchisesUseCase listFranchisesUseCase;
    private final UpdateFranchiseNameUseCase updateFranchiseNameUseCase;
    private final DeleteFranchiseUseCase deleteFranchiseUseCase;

    public FranchiseService(CreateFranchiseUseCase createFranchiseUseCase,
                            GetFranchiseByIdUseCase getFranchiseByIdUseCase,
                            ListFranchisesUseCase listFranchisesUseCase,
                            UpdateFranchiseNameUseCase updateFranchiseNameUseCase,
                            DeleteFranchiseUseCase deleteFranchiseUseCase) {
        this.createFranchiseUseCase = createFranchiseUseCase;
        this.getFranchiseByIdUseCase = getFranchiseByIdUseCase;
        this.listFranchisesUseCase = listFranchisesUseCase;
        this.updateFranchiseNameUseCase = updateFranchiseNameUseCase;
        this.deleteFranchiseUseCase = deleteFranchiseUseCase;
    }

    public Mono<Franchise> create(String name) {
        Franchise franchise = new Franchise();
        franchise.setId(UUID.randomUUID());
        franchise.setName(name);

        return createFranchiseUseCase.execute(franchise);
    }

    public Mono<Franchise> getById(UUID id) {
        return getFranchiseByIdUseCase.execute(id);
    }

    public Flux<Franchise> listAll() {
        return listFranchisesUseCase.execute();
    }

    public Mono<Franchise> updateName(UUID id, String newName) {
        return updateFranchiseNameUseCase.execute(id, newName);
    }

    public Mono<Void> delete(UUID id) {
        return deleteFranchiseUseCase.execute(id);
    }
}
